package processinglibrarymanager;

/**
 *
 * @author Jerome
 */
public class Contribution {

    private final String type;
    private final int minRevision;
    private final String name;
    private final int maxRevision;
    private final String sentence;
    private final String websiteUrl;
    private final String prettyVersion;
    private final String paragraph;
    private final int version;
    private final String authors;
    private final String downloadUrl;
    private final String id;
    private final String categories;

    public String getType() {
        return type;
    }

    public int getMinRevision() {
        return minRevision;
    }

    public String getName() {
        return name;
    }

    public int getMaxRevision() {
        return maxRevision;
    }

    public String getSentence() {
        return sentence;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getPrettyVersion() {
        return prettyVersion;
    }

    public String getParagraph() {
        return paragraph;
    }

    public int getVersion() {
        return version;
    }

    public String getAuthors() {
        return authors;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getId() {
        return id;
    }

    public String getCategories() {
        return categories;
    }
    
    /*
    Example of contribution entry in contribs.txt
    
library
minRevision=0
name=Simple Multi-Touch (SMT)
maxRevision=228
sentence=Multi-touch prototyping and development made simple.
url=http://vialab.science.uoit.ca/SMT/
prettyVersion=4.2
paragraph=SMT provides back-end device support for Tuio, Windows Touch* ( requires [VC2012 Redistributable](http://www.microsoft.com/en-au/download/details.aspx?id=30679) ), SmartSDK*, Leap Motion*, and Mouse Emulation. *Windows-Only
version=23
authors=[Erik Paluka](http://www.erikpaluka.com/), [Kalev Kalda Sikes](http://vialab.science.uoit.ca/portfolio/kalev-kalda-sikes), [Zachary Cook](http://vialab.science.uoit.ca/portfolio/zachary-cook), [Dr. Mark Hancock](http://markhancock.ca/), and [Dr. Christopher Collins](http://vialab.science.uoit.ca/portfolio/christopher-m-collins)
download=http://vialab.science.uoit.ca/smt/dl/SMT.zip
type=library
id=098
categories=I/O
     */
    public Contribution(String type, int minRevision, String name, int maxRevision, String sentence, String websiteUrl, String prettyVersion, String paragraph, int version, String authors, String downladUrl, String id, String categories) {
        this.type = type;
        this.minRevision = minRevision;
        this.name = name;
        this.maxRevision = maxRevision;
        this.sentence = sentence;
        this.websiteUrl = websiteUrl;
        this.prettyVersion = prettyVersion;
        this.paragraph = paragraph;
        this.version = version;
        this.authors = authors;
        this.downloadUrl = downladUrl;
        this.id = id;
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Contribution{" + "type=" + type + ", minRevision=" + minRevision + ", name=" + name + ", maxRevision=" + maxRevision + ", sentence=" + sentence + ", websiteUrl=" + websiteUrl + ", prettyVersion=" + prettyVersion + ", paragraph=" + paragraph + ", version=" + version + ", authors=" + authors + ", downloadUrl=" + downloadUrl + ", id=" + id + ", categories=" + categories + '}';
    }
    
    
}
