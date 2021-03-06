import org.garret.perst.*;
import org.garret.perst.reflect.*;
import java.util.Date;
import java.util.Vector;

class Track extends Persistent 
{ 
    /**
     * Track number
     */
    int no;
    
    /**
     * Album
     * @modifier Indexable
     */
    Album album;

    /**
     * Track name
     * @modifier Indexable    
     */
    String name;

    /**
     * Track duration in seconds
     */
    float duration;

    /**
     * Track is a hit
     * @modifier Indexable    
     */
    boolean hit;

    public void readObject(IInputStream in) {
        super.readObject(in);
        no = in.readInt();
        album = (Album)in.readObject();
        name = in.readString();
        duration = in.readFloat();
        hit = in.readBoolean();
    }

    public void writeObject(IOutputStream out) {
        super.writeObject(out);
        out.writeInt(no);
        out.writeObject(album);
        out.writeString(name);
        out.writeFloat(duration);
        out.writeBoolean(hit);
    }

    private static Type getClassDescriptor() { 
        return new Type(Track.class, Persistent.class, new Class[]{}, new Field[]{
            new Field(Integer.class, "no") {
                public Object get(Object obj) { return new Integer(((Track)obj).no); }
                public void set(Object obj, Object value) { ((Track)obj).no = ((Integer)value).intValue(); }
                public int getInt(Object obj) { return ((Track)obj).no; }
                public void setInt(Object obj, int value) { ((Track)obj).no = value; }
            },
            new Field(Album.class, "album", Modifier.Indexable) {
                public Object get(Object obj) { return ((Track)obj).album; }
                public void set(Object obj, Object value) { ((Track)obj).album = (Album)value; }
            },
            new Field(String.class, "name", Modifier.Indexable) {
                public Object get(Object obj) { return ((Track)obj).name; }
                public void set(Object obj, Object value) { ((Track)obj).name = (String)value; }
            },
            new Field(Float.class, "duration") {
                public Object get(Object obj) { return new Float(((Track)obj).duration); }
                public void set(Object obj, Object value) { ((Track)obj).duration = ((Float)value).floatValue(); }
                public float getFloat(Object obj) { return ((Track)obj).duration; }
                public void setFloat(Object obj, float value) { ((Track)obj).duration = value; }
            },
            new Field(Boolean.class, "hit", Modifier.Indexable) {
                public Object get(Object obj) { return new Boolean(((Track)obj).hit); }
                public void set(Object obj, Object value) { ((Track)obj).hit = ((Boolean)value).booleanValue(); }
                public boolean getBoolean(Object obj) { return ((Track)obj).hit; }
                public void setBoolean(Object obj, boolean value) { ((Track)obj).hit = value; }
            },}, new Method[]{}) {
            public Object newInstance() { return new Track(); }
        };
    }

    public static final Type TYPE = getClassDescriptor();
}

class Album extends Persistent 
{ 
    /**
     * Album name
     * @modifier Indexable    
     */
    String name;
  
    /**
     * Record label released the album
     * @modifier Indexable    
     */
    RecordLabel label;

    /**
     * Album genre
     */
    String genre;

    /**
     * Album release date
     */
    Date release;

    public void readObject(IInputStream in) {
        super.readObject(in);
        name = in.readString();
        label = (RecordLabel)in.readObject();
        genre = in.readString();
        release = in.readDate();
    }

    public void writeObject(IOutputStream in) {
        super.writeObject(in);
        in.writeString(name);
        in.writeObject(label);
        in.writeString(genre);
        in.writeDate(release);
    }

    private static Type getClassDescriptor() { 
        return new Type(Album.class, Persistent.class, new Class[]{}, new Field[]{
            new Field(String.class, "name", Modifier.Indexable) {
                public Object get(Object obj) { return ((Album)obj).name; }
                public void set(Object obj, Object value) { ((Album)obj).name = (String)value; }
            },
            new Field(RecordLabel.class, "label", Modifier.Indexable) {
                public Object get(Object obj) { return ((Album)obj).label; }
                public void set(Object obj, Object value) { ((Album)obj).label = (RecordLabel)value; }
            },
            new Field(String.class, "genre") {
                public Object get(Object obj) { return ((Album)obj).genre; }
                public void set(Object obj, Object value) { ((Album)obj).genre = (String)value; }
            },
            new Field(Date.class, "release") {
                public Object get(Object obj) { return ((Album)obj).release; }
                public void set(Object obj, Object value) { ((Album)obj).release = (Date)value; }
            },}, new Method[]{}) {
            public Object newInstance() { return new Album(); }
        };
    }

    public static final Type TYPE = getClassDescriptor();
}

class RecordLabel extends Persistent 
{ 
    /**
     * Record label company name
     * @modifier Indexable    
     */
    String name;

    String email;
    String phone;
    String address;
    boolean closed;

    public void readObject(IInputStream in) {
        super.readObject(in);
        name = in.readString();
        email = in.readString();
        phone = in.readString();
        address = in.readString();
        closed = in.readBoolean();
    }

    public void writeObject(IOutputStream in) {
        super.writeObject(in);
        in.writeString(name);
        in.writeString(email);
        in.writeString(phone);
        in.writeString(address);
        in.writeBoolean(closed);
    }

    private static Type getClassDescriptor() { 
        return new Type(RecordLabel.class, Persistent.class, new Class[]{}, new Field[]{
            new Field(String.class, "name", Modifier.Indexable) {
                public Object get(Object obj) { return ((RecordLabel)obj).name; }
                public void set(Object obj, Object value) { ((RecordLabel)obj).name = (String)value; }
            },
            new Field(String.class, "email") {
                public Object get(Object obj) { return ((RecordLabel)obj).email; }
                public void set(Object obj, Object value) { ((RecordLabel)obj).email = (String)value; }
            },
            new Field(String.class, "phone") {
                public Object get(Object obj) { return ((RecordLabel)obj).phone; }
                public void set(Object obj, Object value) { ((RecordLabel)obj).phone = (String)value; }
            },
            new Field(String.class, "address") {
                public Object get(Object obj) { return ((RecordLabel)obj).address; }
                public void set(Object obj, Object value) { ((RecordLabel)obj).address = (String)value; }
            },
            new Field(Boolean.class, "closed") {
                public Object get(Object obj) { return new Boolean(((RecordLabel)obj).closed); }
                public void set(Object obj, Object value) { ((RecordLabel)obj).closed = ((Boolean)value).booleanValue(); }
                public boolean getBoolean(Object obj) { return ((RecordLabel)obj).closed; }
                public void setBoolean(Object obj, boolean value) { ((RecordLabel)obj).closed = value; }
            },}, new Method[]{}) {
            public Object newInstance() { return new RecordLabel(); }
        };
    }

    public static final Type TYPE = getClassDescriptor();
}

class QueryExecutionListener extends StorageListener
{
    int nSequentialSearches;
    int nSorts;

    public void sequentialSearchPerformed(String query) {
        nSequentialSearches += 1;
    }        

    public void sortResultSetPerformed(String query) {
        nSorts += 1;
    }        
}


public class JSQLTest 
{
    static final int nLabels = 10;
    static final int nAlbums = 1000;
    static final int nTracksPerAlbum = 10;
    static final int hitsPercent = 1;
    static final int pagePoolSize = 1024*1024;

    public static void main(String[] args) 
    {
        Storage storage = StorageFactory.getInstance().createStorage(); 
        storage.open("jsqltest1.dbs", pagePoolSize);
        Database db = new Database(storage);
        Iterator iterator;

        for (int i = 0; i < nLabels; i++) { 
            RecordLabel label = new RecordLabel();
            label.name = "Label" + i;
            label.email = "contact@" + label.name + ".com";
            label.address = "Country, City, Street";
            label.phone = "+1 123-456-7890";
            label.closed = i == 0;
            db.addRecord(label);
        }        

        for (int i = 0; i < nAlbums; i++) { 
            Album album = new Album();
            album.name = "Album" + i;
            album.label = (RecordLabel)db.select(RecordLabel.class, "name='Label" + (i % nLabels) + "'").next();
            album.genre = "Rock";
            album.release = new Date();
            db.addRecord(album);
            
            for (int j = 0; j < nTracksPerAlbum; j++) { 
                Track track = new Track();
                track.no = j+1;
                track.name = "Track" + j;
                track.album = album;
                track.duration = 3.5f;
                track.hit = (i*nTracksPerAlbum + j) % 100 < hitsPercent;
                db.addRecord(track);                
            }
        }

        QueryExecutionListener listener = new QueryExecutionListener();
        storage.setListener(listener);
        Query query = db.prepare(Track.class, "no > 0 and album.label.name=?");
        int nTracks = 0;
        for (int i = 0; i < nLabels; i++) {
            query.setParameter(1, "Label" + i);
            for (iterator = query.execute(); iterator.hasNext(); iterator.next()) { 
                nTracks += 1;
            }
        }
        Assert.that(nTracks == nAlbums*nTracksPerAlbum);

        String prev = "";
        int n = 0;
        for (iterator = db.select(RecordLabel.class, "order by name"); iterator.hasNext(); n++) 
        {
            RecordLabel label = (RecordLabel)iterator.next();
            Assert.that(prev.compareTo(label.name) < 0);
            prev = label.name;
        }
        Assert.that(n == nLabels);

        n = 0;
        prev = "zzz";
        for (iterator = db.select(RecordLabel.class, "name like 'Label%' order by name desc"); 
             iterator.hasNext(); 
             n++)
        {
            RecordLabel label = (RecordLabel)iterator.next();
            Assert.that(prev.compareTo(label.name) > 0);
            prev = label.name;
        }
        Assert.that(n == nLabels);

        n = 0;
        for (iterator = db.select(RecordLabel.class, "name in ('Label1', 'Label2', 'Label3')"); 
             iterator.hasNext(); 
             iterator.next())
        {
            n += 1;
        }
        Assert.that(n == 3);

        n = 0;
        for (iterator = db.select(RecordLabel.class, "(name = 'Label1' or name = 'Label2' or name = 'Label3') and email like 'contact@%'");
             iterator.hasNext();
             iterator.next())
        {
            n += 1;
        }
        Assert.that(n == 3);

        query = db.prepare(RecordLabel.class, "phone like '+1%' and name in ?");
        Vector list = new Vector(nLabels);
        for (int i = 0; i < nLabels; i++) {
            list.addElement("Label" + i);
        }
        n = 0;
        query.setParameter(1, list);
        for (iterator = query.execute(); iterator.hasNext(); n++) { 
            RecordLabel label = (RecordLabel)iterator.next();
            Assert.that(label.name.equals("Label" + n));
        }
        Assert.that(n == nLabels);        
        
        n = 0;
        for (iterator = db.select(Track.class, "album.label.name='Label1' or album.label.name='Label2'");
             iterator.hasNext();
             n += 1)
        {
            Track track = (Track)iterator.next();
            Assert.that(track.album.label.name.equals("Label1") || track.album.label.name.equals("Label2"));
        }
        Assert.that(n == nAlbums*nTracksPerAlbum*2/nLabels);

        n = 0;
        for (iterator = db.select(Track.class, "hit = true"); iterator.hasNext(); n += 1) {
            Track track = (Track)iterator.next();
            Assert.that(track.hit);
        }
        Assert.that(n == nTracksPerAlbum*nAlbums*hitsPercent/100);

        n = 0;
        for (iterator = db.select(Track.class, "not hit"); iterator.hasNext(); n += 1) {
            Track track = (Track)iterator.next();
            Assert.that(!track.hit);
        }
        Assert.that(n == nTracksPerAlbum*nAlbums*(100-hitsPercent)/100);

        n = 0;
        for (iterator = db.select(RecordLabel.class, "name like 'Label%' and closed = false"); iterator.hasNext(); n += 1) {
            RecordLabel label = (RecordLabel)iterator.next();
            Assert.that(!label.closed);
        }
        Assert.that(n == nLabels-1);

        Assert.that(listener.nSequentialSearches == 0);
        Assert.that(listener.nSorts == 0);

        db.dropTable(Track.class);
        db.dropTable(Album.class);
        db.dropTable(RecordLabel.class);

        storage.close();
    }        
}