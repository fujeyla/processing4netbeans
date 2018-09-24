/**
 *   This file is part of Processing4Netbeans Netbeans plugin, 
 *   initially created by fujeyla.
 *
 *   Processing4Netbeans is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Processing4Netbeans is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Processing4Netbeans.  If not, see <https://www.gnu.org/licenses/>
 */

package org.netbeans.modules.java.processingproject.contributions;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contribution {

    private StringProperty type = new SimpleStringProperty();
    private IntegerProperty minRevision = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty maxRevision = new SimpleIntegerProperty();
    private StringProperty sentence = new SimpleStringProperty();
    private StringProperty websiteUrl = new SimpleStringProperty();
    private StringProperty prettyVersion = new SimpleStringProperty();
    private StringProperty paragraph = new SimpleStringProperty();
    private IntegerProperty version = new SimpleIntegerProperty();
    private StringProperty authors = new SimpleStringProperty();
    private StringProperty downloadUrl = new SimpleStringProperty();
    private StringProperty id = new SimpleStringProperty();
    private StringProperty categories = new SimpleStringProperty();

    public String getType() {
        return type.get();
    }

    public StringProperty getTypeProperty() {
        return type;
    }

    public int getMinRevision() {
        return minRevision.get();
    }

    public IntegerProperty getMinRevisionProperty() {
        return minRevision;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public int getMaxRevision() {
        return maxRevision.get();
    }

    public IntegerProperty getMaxRevisionProperty() {
        return maxRevision;
    }

    public String getSentence() {
        return sentence.get();
    }

    public StringProperty getSentenceProperty() {
        return sentence;
    }

    public String getWebsiteUrl() {
        return websiteUrl.get();
    }

    public StringProperty getWebsiteUrlProperty() {
        return websiteUrl;
    }

    public String getPrettyVersion() {
        return prettyVersion.get();
    }

    public StringProperty getPrettyVersionProperty() {
        return prettyVersion;
    }

    public String getParagraph() {
        return paragraph.get();
    }

    public StringProperty getParagraphProperty() {
        return paragraph;
    }

    public int getVersion() {
        return version.get();
    }

    public IntegerProperty getVersionProperty() {
        return version;
    }

    public String getAuthors() {
        return authors.get();
    }

    public String getDownloadUrl() {
        return downloadUrl.get();
    }

    public StringProperty getDownloadUrlProperty() {
        return downloadUrl;
    }

    public String getId() {
        return id.get();
    }

    public StringProperty getIdProperty() {
        return id;
    }

    public String getCategories() {
        return categories.get();
    }
    
    public StringProperty getCategoriesProperty(){
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
    public Contribution(
            String type,
            int minRevision,
            String name,
            int maxRevision,
            String sentence,
            String websiteUrl,
            String prettyVersion,
            String paragraph,
            int version,
            String authors,
            String downladUrl,
            String id,
            String categories) {
        this.type.set(type);
        this.minRevision.set(minRevision);
        this.name.set(name);
        this.maxRevision.set(maxRevision);
        this.sentence.set(sentence);
        this.websiteUrl.set(websiteUrl);
        this.prettyVersion.set(prettyVersion);
        this.paragraph.set(paragraph);
        this.version.set(version);
        this.authors.set(authors);
        this.downloadUrl.set(downladUrl);
        this.id.set(id);
        this.categories.set(categories);
    }

    @Override
    public String toString() {
        return "Contribution{" + "type=" + type + ", minRevision=" + minRevision + ", name=" + name + ", maxRevision=" + maxRevision + ", sentence=" + sentence + ", websiteUrl=" + websiteUrl + ", prettyVersion=" + prettyVersion + ", paragraph=" + paragraph + ", version=" + version + ", authors=" + authors + ", downloadUrl=" + downloadUrl + ", id=" + id + ", categories=" + categories + '}';
    }

}
