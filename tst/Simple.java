import org.garret.perst.*;
import java.util.*;

// All presistent capable classes should be derived from Persistent base class
class MyPersistentClass  extends Persistent 
{
    public int intKey;    // integer key
    public String strKey; // string key
    public String body;   // non-indexed field

    public String toString() { 
        return intKey + ":" + strKey + ":" + body;
    }
}

// There should one root object in the database. This class should contain
// collections which can be used to access all other objects in the storage.
class MyRootClass extends Persistent 
{
    public FieldIndex<MyPersistentClass> intKeyIndex;  // index on MyPersistentClass.intKey
    public FieldIndex<MyPersistentClass> strKeyIndex;  // index on MyPersistentClass.strKey
    public Index<MyPersistentClass>      foreignIndex; // index on MyPersistentClass which key is not part of this class 

    // Persistent capable class should not use default constructor for initialization, since
    // this constructor is called by Perst each time the object is loaded from the database.
    // So persistent capable class should have not constructor at all (in this case 
    // it will be automatically generated by compiler), either define constructor with non-empty
    // parameter list for object initialization (the most natural way is to pass Storage reference 
    // to this constructor, since Storage in any case is neede to created instances of Perst 
    // collections) and define empty default constructor (in case of using Sun JVM, definition of 
    // empty constructor is not necessary since Perst is able to instantiate persistent object
    // without using default constructor using non-standard Sun's API).
    public MyRootClass(Storage db) { 
        super(db);
        intKeyIndex = db.<MyPersistentClass>createFieldIndex(
                                                             MyPersistentClass.class, // class for which index is defined
                                                             "intKey", // name of indexed field
                                                             true); // unique index
        strKeyIndex = db.<MyPersistentClass>createFieldIndex(MyPersistentClass.class, // class for which index is defined
                                                             "strKey",  // name of indexed field
                                                             false); // index allows duplicates (is not unique)
        foreignIndex = db.<MyPersistentClass>createIndex(int.class, // key type
                                                         false); // index allows duplicates (is not unique)
    }

    // Default constructor is needed for Perst to be able tio instantiate instances of loaded
    // objects. In case of using Sun JVM, it can be skept
    public MyRootClass() {} 
}


public class Simple
{
    static final int pagePoolSize = 32*1024*1024; // database cache size

    static public void main(String[] args) {    
        // get instance of the storage
        Storage db = StorageFactory.getInstance().createStorage();
        // open the database 
        db.open("simple.dbs", pagePoolSize);

        MyRootClass root = (MyRootClass)db.getRoot(); // get storage root
        if (root == null) { 
            // Root is not yet defined: stotage is not initialized
            root = new MyRootClass(db); // create root object
            db.setRoot(root); // register root object
        }
        // Create instance of the persistent capable class.
        // Created instance is not automatically stored in the database: Perst uses 
        // "Persistence by reachability" apporach, which means that persistent capable class with
        // be automatically stored in the database when reference to it is assigned to some other 
        // persistent object (including collections).
        MyPersistentClass obj = new MyPersistentClass();
        obj.intKey = 1;
        obj.strKey = "A.B";
        obj.body = "Hello world";
        
        // It is responsibility of programmer in Perst to maintain indices: add crearted object
        // to the proper indices, exclude it from the indices when key fields are changed or object 
        // is deleted.
        root.intKeyIndex.put(obj); // add object to index on intKey field
        root.strKeyIndex.put(obj); // add object to index in strKey field
        // To explictiely specify value of the key it is necessary to create instance of 
        // org.garret.perst.Key class which overloaded constructor will create key of correspondent 
        // type
        root.foreignIndex.put(new Key(1001), obj);

        // Commit current transaction. It should not be done after insertion of each object since 
        // transaction commit is expensibe operation and too frequent commits leans to bad performance.
        // It is preferrable to group sequence of logicaslly relation operations into one transaction
        db.commit();

        // Locate object by key. Since index is unique it is possible to use get method which returns
        // single object or null if object with such key is not found
        obj = root.intKeyIndex.get(new Key(1));
        System.out.println("Exact match search by intKey: " + obj);

        // Since strKeyIndex is not unique, it is necessary to user GenericIndex.get method which returns 
        // array of objects. It takes minimal and maximal value of the key as parameters.
        // In case of strict match search, the same key should be specified as minimam and maximim
        Key key = new Key("A.B"); 
        ArrayList<MyPersistentClass> result = root.strKeyIndex.getList(key, key);
        for (int i = 0; i < result.size(); i++) { 
            System.out.println("Exact match search by strKey: " + result.get(i));
        }
        
        // Get iterator through records belonging to specified key range in ascent order
        for (MyPersistentClass o:root.foreignIndex.iterator(
                                                                 new Key(100, true), // inclusive low boundary
                                                                 new Key(10000, false), // exclusive high boundary
                                                                 Index.ASCENT_ORDER)) // ascent order 
        {
            System.out.println("Range search by foreign key: " + o);
        }

        // Locate all objects which strKey starts with prefix "A."
        for (MyPersistentClass o:root.strKeyIndex.prefixIterator("A.")) { 
            System.out.println("Search by prefix: " + o);
        }
        
        // Locate all objects which strKey is prefix of "A.B.C"
        result = root.strKeyIndex.prefixSearchList("A.B.C");
        for (int i = 0; i < result.size(); i++) { 
            System.out.println("Locate prefix: " + result.get(i));
        }
        
        // To update object it is necessary first to exclude it from the index:
        root.intKeyIndex.remove(obj);
        // ... then update the field
        obj.intKey = 2;
        // ... and insert it in the index once again
        root.intKeyIndex.put(obj);


        // When object is removed, it should be first excluded from all indices
        root.intKeyIndex.remove(obj); // when object is removed from field index, it is not neccesary explicitely specify key
        root.strKeyIndex.remove(obj);
        root.foreignIndex.remove(new Key(1001), obj); // ... and here key has to be explicitely specified
        obj.deallocate(); // explicit deallocation of object (Perst garbage collection can be used instead of explicit deallocation

        // Close the database
        db.close();
    }
}
        
        