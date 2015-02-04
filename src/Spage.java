
public class Spage {

	final static String etudiantUrl = "http://jobs-stages.letudiant.fr/stages-etudiants.html"; 
	
	public static void main(String[] args) throws Exception {
		EtudiantSpider eSpider = new EtudiantSpider(etudiantUrl);
		eSpider.crawlData(new String[] {"Informatique", "Science"}, "3", "", 4);

	}

}
