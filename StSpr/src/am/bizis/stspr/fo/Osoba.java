package am.bizis.stspr.fo;

import java.util.Date;
import java.util.HashSet;

public class Osoba {
	
	protected String jmeno,druhe,prijmeni;
	private final String RODNE_PRIJMENI;
	private final RodneCislo RODNE_CISLO;
	protected HashSet<Titul> tituly;
	private Vzdelani nejvyssiDosazene;
	private String datovaschranka;
	private Titul nyni=null;

	/* KONSTRUKTORY */
	public Osoba(String jmeno, String prijmeni,RodneCislo rc){
		this(jmeno,null,prijmeni,prijmeni,rc);
	}
	
	public Osoba(String jmeno,String druhe,String prijmeni,RodneCislo rc){
		this(jmeno,prijmeni,rc);
		this.druhe=druhe;
	}
	
	public Osoba(String jmeno,String druhe,String rodne,String prijmeni,RodneCislo rc){
		this.RODNE_CISLO=rc;
		this.RODNE_PRIJMENI=rodne;
		this.jmeno=jmeno;
		this.prijmeni=prijmeni;
		this.tituly=new HashSet<Titul>();
		this.nejvyssiDosazene=Vzdelani.ZADNE;
	}

	/* metody */
	public String getKrestni(){
		return jmeno;
	}
	
	public String getPrijmeni(){
		return prijmeni;
	}
	
	public RodneCislo getRodneCislo(){
		return RODNE_CISLO;	
	}
	
	public Date getNarozeni(){
		return RODNE_CISLO.getNarozeni();
	}
	
	public Pohlavi getPohlavi(){
		return RODNE_CISLO.getPohlavi();
	}
	
	/**
	 * @return druhe jmeno
	 */
	public String getDruhe(){
		return druhe;
	}
	
	/**
	 * @return zda ma druhe jmeno
	 */
	public boolean hasDruhe(){
		if (druhe==null) return false;
		else return true;
	}
	
	/**
	 * Zmena jmena
	 */
	public void setJmeno(String krestni,String druhe,String prijmeni){
		this.jmeno=krestni;
		this.druhe=druhe;
		this.prijmeni=prijmeni;
	}
	
	public String getRodnePrijmeni(){
		return this.RODNE_PRIJMENI;
	}
	
	/**
	 * @return bylo-li zmeneno prijmeni
	 */
	public boolean zmenaPrijmeni(){
		if(this.prijmeni.equals(this.RODNE_PRIJMENI)) return true;
		else return false;
	}
	
	/**
	 * Zaregistruje dalsi akademicky titul
	 * @param t
	 */
	public void addTitul(Titul t){
		this.tituly.add(t);
		if(t.getLevel().getLevel()<this.nejvyssiDosazene.getLevel()) this.nejvyssiDosazene=t.getLevel();
	}
	
	public HashSet<Titul> getTituly(){
		return this.tituly;
	}
	
	/**
	 * Slouzi k zadani zakladniho a stredniho vzdelani, vyssi urovne se zadavaji pres titul
	 * @param v uroven nejvyssiho dosazeneho vzdelani
	 * @throws IllegalArgumentException pokud zadavana uroven vzdelani je vyssi nez stredni
	 */
	public void setNejvyssiVzdelani(Vzdelani v){
		if(v.getLevel()<=Vzdelani.STREDNI.getLevel()) this.nejvyssiDosazene=v;
		else throw new IllegalArgumentException("Vyssi nez stredni vzdelani se zadava pres titul!");
	}
		
	/**
	 * @return nejvyssi dosazene vzdelani
	 */
	public Vzdelani getNejvyssiVzdelani(){
		return this.nejvyssiDosazene;
	}
	
	/**
	 * Plna podoba jmena vcetne titulu
	 */
	@Override
	public String toString(){
		String jmeno="";
		getTitul();
		if(nyni!=null&&nyni.predJmenem) jmeno+=nyni.zkratka+" ";
		jmeno+=this.jmeno+" ";
		if(this.druhe!=null) jmeno+=this.druhe+" ";
		jmeno+=this.prijmeni;
		if (nyni!=null&&!nyni.predJmenem) jmeno+=" "+nyni.zkratka;
		return jmeno;
	}

	public boolean hasDatovaSchranka(){
		if(datovaschranka.equals(null)) return true;
		else return false;
	}
	/**
	 * @return the datovaschrannka
	 */
	public String getDatovaSchranka() {
		return datovaschranka;
	}

	/**
	 * @param datovaschrannka the datovaschrannka to set
	 */
	public void setDatovaSchranka(String datovaschranka) {
		this.datovaschranka = datovaschranka;
	}
	
	/**
	 * Vrati nejvyssi titul
	 * @return nejvyssi titul dane osoby, v pripade vicero rigoroznich zkousek vsechny oddelene carkou
	 */
	public String getTitul(){
		String tit=null;
		for(Titul t:tituly){
			if(t.level.getLevel()>nyni.level.getLevel()) {
				nyni=t;
				tit=t.zkratka;
			} else if(t.level.getLevel()==nyni.level.getLevel()){
				tit+=", "+t.zkratka;
			}
		}
		return tit;
	}
	
	public Titul getNyni(){
		return nyni;
	}
}
