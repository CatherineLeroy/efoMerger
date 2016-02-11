package uk.ac.ebi.spot.efoMerger;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLOntologyImportsClosureSetProvider;
import org.semanticweb.owlapi.util.OWLOntologyMerger;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import uk.ac.ebi.spot.efoMerger.utlis.OntologyIRI2FilePathEnum;

import java.io.File;

/**
 * Created by catherineleroy on 11/02/2016.
 */
public class Merger {


    private String directory = null;

    public void merge() throws OWLOntologyCreationException, OWLOntologyStorageException {

        File efoFile = new File(directory + "/efo_release_candidate.owl");
        if (!efoFile.exists()) {
            throw new IllegalArgumentException("Couldn't find file " + directory + "/efo_release_candidate.owl");
        }
//
        //Import all owl files (efo_disease_module.owl, efo_ordo_module.owl ...).
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        for (OntologyIRI2FilePathEnum enumeration : OntologyIRI2FilePathEnum.values()) {
            manager.addIRIMapper(
                    new SimpleIRIMapper(IRI.create(enumeration.getIri()),
                            IRI.create("file:" + directory + "/" + enumeration.getFileName())));
        }

        // Initiate everything for efo.owl searching
        OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();


        OWLOntologyDocumentSource owlOntologyDocumentSource = new FileDocumentSource(efoFile);
        OWLOntology efo = manager.loadOntologyFromOntologyDocument(owlOntologyDocumentSource, config);

        OWLOntologySetProvider set = new OWLOntologyImportsClosureSetProvider(manager, efo);
        OWLOntologyMerger owlOntologyMerger = new OWLOntologyMerger(set);
        OWLOntologyManager efoMergedManager = OWLManager.createOWLOntologyManager();
        OWLOntology efoMergedOntology = owlOntologyMerger.createMergedOntology(efoMergedManager,IRI.create("http://www.ebi.ac.uk/efo/efo.owl"));
        File file = new File(directory + "/efo_merged.owl");
        efoMergedManager.saveOntology(efoMergedOntology, IRI.create(file));

    }


    public void getOptions(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("d", true, "The directory where the efo_release_candidate.owl file and imports are.");

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse( options, args);


        if(cmd.hasOption("d")) {
            directory = cmd.getOptionValue("d");
        }
        else {
            throw new Exception ("Option -i providing path to the efo-release-candidate.owl file is required.");
        }

    }


    public static void main(String[] args) throws Exception {
        //"/Users/catherineleroy/Documents/github_project/ExperimentalFactorOntology/ExFactorInOWL/releasecandidate"

        Merger merger = new Merger();
        merger.getOptions(args);
        merger.merge();
    }
}
