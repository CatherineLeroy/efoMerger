package uk.ac.ebi.spot.efoMerger.utlis;

public enum OntologyIRI2FilePathEnum {

    ORDO_AXIOM("http://www.ebi.ac.uk/efo/efoordoaxioms.owl","efoordoaxioms.owl", true),
    ORDO_MODULE("http://www.orpha.net/ontology/efo_ordo_module.owl","efo_ordo_module.owl", false),
    DISEASE_MODULE("http://www.ebi.ac.uk/efo/efoDiseaseModule","efo_disease_module.owl", false),
    DISEASE_AXIOM("http://www.ebi.ac.uk/efo/efoDiseaseAxiom","efoDiseaseAxioms.owl", true),
    EFO("http://www.ebi.ac.uk/efo","efo_release_candidate.owl", false),
    IDO_PROTEGE("http://protege.stanford.edu/plugins/owl/dc/protege-dc.owl","protege-dc.owl", false),
    IDO_MAIN("http://purl.obolibrary.org/obo/ido/ido-main.owl","ido-main.owl", false),
    IDO_OBSOLETE("http://infectious-disease-ontology.googlecode.com/svn/releases/2014-08-01/ido-obsolete.owl","ido-obsolete.owl", false);

    private String iri;
    private String fileName;
    private Boolean isAxiomOnly;


    private OntologyIRI2FilePathEnum(String iri, String fileName, Boolean isAxiomOnly){
        this.iri = iri;
        this.fileName = fileName;
        this.isAxiomOnly = isAxiomOnly;
    }
    public String getIri(){
        return iri;
    }
    public String getFileName(){
        return fileName;
    }
    public Boolean isAxiomOnly(){return isAxiomOnly;}

    public static OntologyIRI2FilePathEnum getForIri(String iri){
        for(OntologyIRI2FilePathEnum ontologyIRI2FilePathEnum : OntologyIRI2FilePathEnum.values()){
            if(ontologyIRI2FilePathEnum.getIri().toString().equals(iri)){
                return ontologyIRI2FilePathEnum;
            }
        }
        return null;
    }

}
