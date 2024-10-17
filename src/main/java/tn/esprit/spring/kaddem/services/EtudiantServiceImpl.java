package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class EtudiantServiceImpl implements IEtudiantService{
	@Autowired
	EtudiantRepository etudiantRepository ;
	@Autowired
	ContratRepository contratRepository;
	@Autowired
	EquipeRepository equipeRepository;
    @Autowired
	DepartementRepository departementRepository;
	public List<Etudiant> retrieveAllEtudiants(){
	return (List<Etudiant>) etudiantRepository.findAll();
	}

	public Etudiant addEtudiant (Etudiant e){
		return etudiantRepository.save(e);
	}

	public Etudiant updateEtudiant (Etudiant e){
		return etudiantRepository.save(e);
	}

	public Etudiant retrieveEtudiant(Integer  idEtudiant){
		return etudiantRepository.findById(idEtudiant).get();
	}

	public void removeEtudiant(Integer idEtudiant){
	Etudiant e=retrieveEtudiant(idEtudiant);
	etudiantRepository.delete(e);
	}

	public void assignEtudiantToDepartement (Integer etudiantId, Integer departementId){
        Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
        Departement departement = departementRepository.findById(departementId).orElse(null);
        etudiant.setDepartement(departement);
        etudiantRepository.save(etudiant);
	}
	@Transactional
	public Etudiant addAndAssignEtudiantToEquipeAndContract(Etudiant e, Integer idContrat, Integer idEquipe){
		Contrat c=contratRepository.findById(idContrat).orElse(null);
		Equipe eq=equipeRepository.findById(idEquipe).orElse(null);
		c.setEtudiant(e);
		eq.getEtudiants().add(e);
return e;
	}

	public 	List<Etudiant> getEtudiantsByDepartement (Integer idDepartement){
return  etudiantRepository.findEtudiantsByDepartement_IdDepart((idDepartement));
	}


	//les nouveux méthodes ajouter


	public void assignEtudiantsToEquipe(List<Integer> etudiantIds, Integer equipeId) {
		Equipe equipe = equipeRepository.findById(equipeId).orElse(null);
		for (Integer etudiantId : etudiantIds) {
			Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
			if (etudiant != null) {
				equipe.getEtudiants().add(etudiant);
			}
		}
		equipeRepository.save(equipe);
	}

	public List<Etudiant> findEtudiantsByDepartement(Integer departementId) {
		// Vérifier si l'ID du département est valide
		if (departementId <= 0) {
			System.out.println("Aucun département trouvé pour l'ID : " + departementId);
			return Collections.emptyList(); // Retourner une liste vide
		}

		List<Etudiant> etudiants = etudiantRepository.findEtudiantsByDepartement_IdDepart(departementId);

		// Affichage du nombre d'étudiants trouvés
		if (etudiants.isEmpty()) {
			System.out.println("Aucun étudiant trouvé pour le département ID : " + departementId);
		} else {
			System.out.println("Il y a " + etudiants.size() + " étudiant(s) dans le département ID : " + departementId + ".");
		}

		return etudiants;
	}

	public long countEtudiantsInDepartement(Integer departementId) {
		// Vérifier si l'ID du département est valide
		if (departementId <= 0) {
			System.out.println("Aucun département trouvé pour l'ID : " + departementId);
			return 0; // Retourner 0 si l'ID est invalide
		}

		long count = etudiantRepository.countEtudiantsByDepartement_IdDepart(departementId);

		// Affichage du nombre d'étudiants trouvés
		if (count == 0) {
			System.out.println("Aucun étudiant trouvé pour le département ID : " + departementId);
		} else {
			System.out.println("Il y a " + count + " étudiant(s) dans le département ID : " + departementId + ".");
		}

		return count;
	}




}
