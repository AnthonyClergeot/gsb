package gsb.modele;

import gsb.entites.CompteRendu;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class ModeleListeCR extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	
	private List<CompteRendu> comptesRendus = null ;
	private final String[] entetes = {"Numéro","Praticien","Ville","Date de visite", "Date de rédaction", "Afficher CR"} ;
	
	public ModeleListeCR() {
		super() ;
		System.out.println("ModeleListeCR::ModeleListeCR()") ;
	}
	
	public List<CompteRendu> getComptesRendus(String vis_matricule, String mois, String annee) {
		System.out.println("ModeleListeCR::getComptesRendus("+ vis_matricule + ", " + mois + ", " + annee +")");
		try {
			comptesRendus = ModelGsb.getModele().getComptesRendusVisiteurs(vis_matricule, mois, annee) ;
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null,e.getMessage()) ;
			
			e.printStackTrace();
		}
		return comptesRendus ;
	}
	
	@Override
	public int getRowCount() {
		System.out.println("ModeleListeCR::getRowCount()") ;
		return this.comptesRendus.size() ;
	}

	@Override
	public int getColumnCount() {
		System.out.println("ModeleListeCR::getColumnCount()") ;
		return this.entetes.length ;
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		System.out.println("ModeleListeCR::getColumnName()") ;
		return this.entetes[columnIndex] ;
	}
	
	public Class<?> getColumnClass(int columnIndex) {
		System.out.println("ModeleListeCR::getColumnClass()") ;
		
		switch(columnIndex) {
		
			case 0 : 
				return int.class ;
			
			case 1 : 
				return String.class ;
			
			case 2 : 
				return String.class  ;
			
			case 3 : 
				return Date.class ;
				
			case 4 : 
				return Date.class ;
				
			case 5 : 
				return JButton.class ;
			
			default :
				return Object.class ;
				
		}
		
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		System.out.println("ModeleListeCR::isCellEditable("+rowIndex+","+columnIndex+")") ;
		if(columnIndex == 5) {
			return true ;
		}
		else {
			return false ;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		System.out.println("ModeleListeCR::getValueAt("+rowIndex+","+columnIndex+")") ;
		
		Object resultat = null ;
		
		switch(columnIndex) {
		
			case 0 : 
				resultat = this.comptesRendus.get(rowIndex).getRapNum() ;
				break ;
			
			case 1 : 
				try {
					resultat = ModelGsb.getModele().getNomPraticien(this.comptesRendus.get(rowIndex).getPraNum()) ;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break ;
			
			case 2 : 
				try {
					resultat = ModelGsb.getModele().getVillePraticien(this.comptesRendus.get(rowIndex).getPraNum()) ;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break ;
			
			case 3 : 
				resultat = this.comptesRendus.get(rowIndex).getRapDateVisite() ;
				break ;
				
			case 4 :
				resultat = this.comptesRendus.get(rowIndex).getRapDateRedaction() ;
				break ;
			
		}
		
		return resultat ;
	}
	
	public void actualiser(){
		System.out.println("ModeleListeCR::actualiser()") ;
		this.fireTableDataChanged();
	} 
	
	public CompteRendu getCompteRendu(int indiceLigne) {
		return this.comptesRendus.get(indiceLigne) ;
	}

}
