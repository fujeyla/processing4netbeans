package processinglibrarymanager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ContributionsReader {

    private static final Logger LOGGER = Logger.getLogger(ContributionsReader.class.getName());
    
    private final String filename;

    public String getFilename() {
        return filename;
    }

    public ContributionsReader(String filename) {
        this.filename = filename;
    }

    public List<Contribution> read() throws IOException {

        List<Contribution> contributions = new ArrayList<>();

        FileInputStream fis = new FileInputStream(filename);
        String type = null;
        int minRevision = -1;
        String name = null;
        int maxRevision = -1;
        String sentence = null;
        String websiteUrl = null;
        String prettyVersion = null;
        String paragraph = null;
        int version = -1;
        String authors = null;
        String downloadUrl = null;
        String id = null;
        String categories = null;

        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        while ((line = br.readLine()) != null) {
            try {
                if (line.trim().length() > 0 && line.indexOf('=') == -1) {
                    type = line;
                }
                if (line.startsWith("minRevision")) {
                    minRevision = Integer.parseInt(line.substring(line.indexOf('=') + 1));
                }
                if (line.startsWith("name")) {
                    name = line.substring(line.indexOf('=') + 1);
                }
                if (line.startsWith("maxRevision")) {
                    maxRevision = Integer.parseInt(line.substring(line.indexOf('=') + 1));
                }
                if (line.startsWith("sentence")) {
                    sentence = line.substring(line.indexOf('=') + 1);
                }
                if (line.startsWith("url")) {
                    websiteUrl = line.substring(line.indexOf('=') + 1);
                }
                if (line.startsWith("prettyVersion")) {
                    prettyVersion = line.substring(line.indexOf('=') + 1);
                }
                if (line.startsWith("paragraph")) {
                    paragraph = line.substring(line.indexOf('=') + 1);
                }
                if (line.startsWith("version")) {
                    version = Integer.parseInt(line.substring(line.indexOf('=') + 1));
                }
                if (line.startsWith("authors")) {
                    authors = line.substring(line.indexOf('=') + 1);
                }
                if (line.startsWith("download")) {
                    downloadUrl = line.substring(line.indexOf('=') + 1);
                }
                if (line.startsWith("id")) {
                    id = line.substring(line.indexOf('=') + 1);
                }
                if (line.startsWith("categories")) {
                    categories = line.substring(line.indexOf('=') + 1);
                }

                if (line.trim().length() == 0) {
                    if ("library".equals(type)) {
                        Contribution contribution = new Contribution(type, minRevision, name, maxRevision, sentence, websiteUrl, prettyVersion, paragraph, version, authors, downloadUrl, id, categories);
                        contributions.add(contribution);
                        type = null;
                        minRevision = -1;
                        name = null;
                        maxRevision = -1;
                        sentence = null;
                        websiteUrl = null;
                        prettyVersion = null;
                        paragraph = null;
                        version = -1;
                        authors = null;
                        downloadUrl = null;
                        id = null;
                        categories = null;
                    }
                }
            }catch(Exception e){
                LOGGER.warning("unable to read line: " + line + " for library name: " + name);
            }
        }

        br.close();
        return contributions;
    }

}
