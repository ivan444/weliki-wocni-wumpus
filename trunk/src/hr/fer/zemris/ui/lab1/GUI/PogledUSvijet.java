package hr.fer.zemris.ui.lab1.GUI;

import hr.fer.zemris.ui.lab1.AgentPolje;
import hr.fer.zemris.ui.lab1.CentralnaInformacijskaAgencija;
import hr.fer.zemris.ui.lab1.SadrzajPolja;
import hr.fer.zemris.ui.lab1.VisaSila;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 * Prikaz svijeta.
 */
public class PogledUSvijet extends JFrame implements IChangeListener {
	private static final long serialVersionUID = 1L;
	
	private JTextField txtSirinaSvijeta;
	private JTextField txtVisinaSvijeta;
	private JTextField txtPCudovista;
	private JTextField txtPJama;
	private JTextPane txtReport;
	private JTextPane txtBazaZnanja;
	private JPanel agentovPogled;
	private JPanel stvarniPogled;
	private JPanel desniDio;
	private boolean svijetJeSpreman;
	
	public PogledUSvijet() {
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	CentralnaInformacijskaAgencija.getCIA().ukloniPretplatu(PogledUSvijet.this);
                dispose();
            }
        });
		svijetJeSpreman = false;
		setTitle("Ultimativno rješenje za traženje zlata i svih zlatnih proizvoda");
		CentralnaInformacijskaAgencija.getCIA().pretplataNaPoruke(this);
		CentralnaInformacijskaAgencija.getCIA().pretplataNaStanjeSvijeta(this);
		initGUI();
	}
	
	/**
	 * Stvaranje korisničkog sučelja.
	 */
	@SuppressWarnings("serial")
	private void initGUI() {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}
        this.setLocationByPlatform(true);
        this.setPreferredSize(new Dimension(900, 700));
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		this.getContentPane().setLayout(new BorderLayout());
		
		// POCETAK: Gornja traka
		JPanel gornjiDio = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));

//		JButton start = new JButton(new AbstractAction() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				start();
//			}
//		});
//		start.setText("Pokreni potragu");
//		gornjiDio.add(start);
		
		JButton korak = new JButton(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				korak();
			}
		});
		korak.setText("Napravi korak");
		gornjiDio.add(korak);
		
//		JButton pause = new JButton(new AbstractAction() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				pause();
//			}
//		});
//		pause.setText("Pauza");
//		gornjiDio.add(pause);
		
		JButton newWorld = new JButton(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stvoriNoviSvijet();
			}
		});
		newWorld.setText("Novi svijet");
		gornjiDio.add(newWorld);
		
		JButton clear = new JButton(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				zaboraviObavijesti();
			}
		});
		clear.setText("Ukloni ispis");
		gornjiDio.add(clear);
		
		this.getContentPane().add(gornjiDio, BorderLayout.NORTH);
		// KRAJ: Gornja traka
		
		// POCETAK: Lijevi izbornik (parametri)
		JPanel parametri = new JPanel(new GridLayout(3, 1, 2, 5));
		JPanel velicinaParam = new JPanel(new GridLayout(4, 1, 0, 2));
		velicinaParam.add(new JLabel("Širina svijeta:"));
		this.txtSirinaSvijeta = new JTextField();
		this.txtSirinaSvijeta.setPreferredSize(new Dimension(100, 15));
		velicinaParam.add(txtSirinaSvijeta);
		velicinaParam.add(new JLabel("Visina svijeta:"));
		this.txtVisinaSvijeta = new JTextField();
		this.txtVisinaSvijeta.setPreferredSize(new Dimension(100, 15));
		velicinaParam.add(txtVisinaSvijeta);
		velicinaParam.setPreferredSize((new Dimension(150, 0)));
		velicinaParam.setBorder(BorderFactory.createTitledBorder("Veličina svijeta"));
		parametri.add(velicinaParam);
		
		JPanel vjerojatnostiParam = new JPanel(new GridLayout(4, 1, 0, 2));
		vjerojatnostiParam.setBorder(BorderFactory.createTitledBorder("Vjerojatnosti pojava"));
		vjerojatnostiParam.add(new JLabel("Čudovišta:"));
		this.txtPCudovista = new JTextField();
		this.txtPCudovista.setPreferredSize(new Dimension(100, 15));
		vjerojatnostiParam.add(txtPCudovista);
		vjerojatnostiParam.add(new JLabel("Jama:"));
		this.txtPJama = new JTextField();
		this.txtPJama.setPreferredSize(new Dimension(100, 15));
		vjerojatnostiParam.add(txtPJama);
		velicinaParam.setPreferredSize((new Dimension(150, 0)));
		parametri.add(vjerojatnostiParam);
		
		JPanel legenda = new JPanel(new GridLayout(4, 1, 0, 2));
		legenda.setBorder(BorderFactory.createTitledBorder("Legenda"));
		JLabel legendaElm = null;
		
		legendaElm = new JLabel("Vjetar, smrad, sjaj");
		legendaElm.setIconTextGap(2);
		legendaElm.setIcon(new ImageIcon("slike/VSmSj.png"));
		legenda.add(legendaElm);
		
		legendaElm = new JLabel("<html>Potencijalna jama,<br>čudovište, zlato</html>");
		legendaElm.setIconTextGap(2);
		legendaElm.setIcon(new ImageIcon("slike/JCZ.png"));
		legenda.add(legendaElm);
		
		legendaElm = new JLabel("Sigurno čudovište");
		legendaElm.setIconTextGap(2);
		legendaElm.setIcon(new ImageIcon("slike/cudoviste.png"));
		legenda.add(legendaElm);
		
		legendaElm = new JLabel("Sigurno jama");
		legendaElm.setIconTextGap(2);
		legendaElm.setIcon(new ImageIcon("slike/jama.png"));
		legenda.add(legendaElm);
		
		parametri.add(legenda);
		
		this.getContentPane().add(parametri, BorderLayout.WEST);
		// KRAJ: Lijevi izbornik (parametri)

		// POCETAK: Donji dio, ispis rezultata.
		JPanel donjiDio = new JPanel(new BorderLayout());
		donjiDio.setPreferredSize(new Dimension(400, 100));
		this.txtReport = new JTextPane();
		this.txtReport.setEditable(false);
		JScrollPane reportScroll = new JScrollPane(this.txtReport);
		reportScroll.setBorder(BorderFactory.createTitledBorder("Ispis"));
		donjiDio.add(reportScroll, BorderLayout.CENTER);
		this.getContentPane().add(donjiDio, BorderLayout.SOUTH);
		predefiniraneVrijednosti();
		// KRAJ: Donji dio, ispis rezultata.
		
		stvoriNoviSvijet();
		
		pack();
		svijetJeSpreman = true;
	}
	
	/**
	 * Postavljanje predefiniranih vrijednosti za stvaranje novog svijeta.
	 */
	private void predefiniraneVrijednosti() {
		txtPCudovista.setText("0.15");
		txtPJama.setText("0.15");
		txtSirinaSvijeta.setText("5");
		txtVisinaSvijeta.setText("5");
	}
	
	
	private void zaboraviObavijesti() {
		this.txtReport.setText("");
	}
	
	/**
	 * Stvaranje novog svijeta.
	 * 
	 * @return True ako je svijet stvoren, inače false.
	 */
	private boolean stvoriNoviSvijet() {
		zaboraviObavijesti();
		this.svijetJeSpreman = false;
		int apSirina;
		int apVisina;
		try {
			apSirina = Integer.parseInt(txtSirinaSvijeta.getText());
			apVisina = Integer.parseInt(txtVisinaSvijeta.getText());
		} catch (NumberFormatException ne) {
			interakcija("Neispravan unos visine ili širine!");
			return false;
		}
		
		double pCudovista;
		double pJama;
		try {
			pCudovista = Double.parseDouble(txtPCudovista.getText());
			pJama = Double.parseDouble(txtPJama.getText());
		} catch (NumberFormatException ne) {
			interakcija("Neispravan unos postotka čudovišta ili jama!");
			return false;
		}
		
		VisaSila.get().stvoriSvijet(apSirina, apVisina, pCudovista, pJama);
		prikaziNoviSvijet();
		
		this.svijetJeSpreman = true;
		
		return true;
	}
	
	private void prikaziNoviSvijet() {
		if (agentovPogled != null) {
			getContentPane().remove(agentovPogled);
		}
		if (desniDio != null) {
			getContentPane().remove(desniDio);
		}
		int apSirina = VisaSila.get().getSvijet().getVelicinaSvijeta().width;
		int apVisina = VisaSila.get().getSvijet().getVelicinaSvijeta().height;
		
		agentovPogled = new JPanel(new GridLayout(apVisina, apSirina, 0, 0));
		stvarniPogled = new JPanel(new GridLayout(apVisina, apSirina, 0, 0));
		Point agentPoz = VisaSila.get().getAgent().getPozicija();
		for (int y = 1; y <= apVisina; y++) {
			for (int x = 1; x <= apSirina; x++) {
				JLabel polje = new JLabel();
				JLabel poljeAp = new JLabel();
				
				postaviSlike(x, y, agentPoz, polje, poljeAp);
				
				agentovPogled.add(poljeAp);
				stvarniPogled.add(polje);
			}
		}
		
		getContentPane().add(agentovPogled, BorderLayout.CENTER);
		
		desniDio = new JPanel(new GridLayout(2,1));
		desniDio.add(stvarniPogled);
		txtBazaZnanja = new JTextPane();
		txtBazaZnanja.setEditable(false);
		txtBazaZnanja.setPreferredSize(new Dimension(150, 150));
		desniDio.add(new JScrollPane(txtBazaZnanja));
		this.getContentPane().add(desniDio, BorderLayout.EAST);
		
		desniDio.revalidate();
		agentovPogled.revalidate();
	}
	
	/**
	 * Prikaz promjena u postojećem svijetu.
	 */
	private void prikaziPromijenjeniSvijet() {
		if (!svijetJeSpreman) return;
		int apSirina = VisaSila.get().getSvijet().getVelicinaSvijeta().width;
		int apVisina = VisaSila.get().getSvijet().getVelicinaSvijeta().height;
		Point agentPoz = VisaSila.get().getAgent().getPozicija();
		
		Component[] comps = stvarniPogled.getComponents();
		Component[] compsAp = agentovPogled.getComponents();
		
		for (int y = 1; y <= apVisina; y++) {
			for (int x = 1; x <= apSirina; x++) {
				JLabel polje = (JLabel) comps[(y-1)*apSirina+(x-1)];
				JLabel poljeAp = (JLabel) compsAp[(y-1)*apSirina+(x-1)];
				
				postaviSlike(x, y, agentPoz, polje, poljeAp);
			}
		}
		desniDio.revalidate();
		agentovPogled.revalidate();
		
	}
	
	private void postaviSlike(int x, int y, Point agentPoz, JLabel polje, JLabel poljeAp) {
		if (x == agentPoz.x && y == agentPoz.y) {
			if (!VisaSila.get().getAgent().isZivim()) {
				polje.setIcon(new ImageIcon("slike/agentMrtav.png"));
				poljeAp.setIcon(new ImageIcon("slike/agentMrtav.png"));
				
			} else if (VisaSila.get().getAgent().isNasaoZlato()) {
				polje.setIcon(new ImageIcon("slike/agentZlato.png"));
				poljeAp.setIcon(new ImageIcon("slike/agentZlato.png"));
				
			} else {
				polje.setIcon(new ImageIcon("slike/agent.png"));
				poljeAp.setIcon(new ImageIcon("slike/agent.png"));
				
			}
			
			return;
		}
		AgentPolje ap = VisaSila.get().getAgent().opisiPolje(new Point(x, y));
		if (ap.isPosjeceno()) {
			if (ap.isVjetrovito() && ap.isSmrdljivo() && ap.isSjajno()) {
				poljeAp.setIcon(new ImageIcon("slike/VSmSj.png"));
				
			} else if (ap.isVjetrovito() && ap.isSmrdljivo() && !ap.isSjajno()) {
				poljeAp.setIcon(new ImageIcon("slike/VSm.png"));
				
			} else if (ap.isVjetrovito() && !ap.isSmrdljivo() && ap.isSjajno()) {
				poljeAp.setIcon(new ImageIcon("slike/VSj.png"));
				
			} else if (ap.isVjetrovito() && !ap.isSmrdljivo() && !ap.isSjajno()) {
				poljeAp.setIcon(new ImageIcon("slike/V.png"));
				
			} else if (!ap.isVjetrovito() && ap.isSmrdljivo() && ap.isSjajno()) {
				poljeAp.setIcon(new ImageIcon("slike/SmSj.png"));
				
			} else if (!ap.isVjetrovito() && ap.isSmrdljivo() && !ap.isSjajno()) {
				poljeAp.setIcon(new ImageIcon("slike/Sm.png"));
				
			} else if (!ap.isVjetrovito() && !ap.isSmrdljivo() && ap.isSjajno()) {
				poljeAp.setIcon(new ImageIcon("slike/Sj.png"));
				
			} else {
				poljeAp.setIcon(new ImageIcon("slike/tocka.png"));
			}
			
		} else if (ap.isZlato()) {
			polje.setIcon(new ImageIcon("slike/zlato.png"));
			
		} else if (ap.isCudoviste()) {
			poljeAp.setIcon(new ImageIcon("slike/cudoviste.png"));
			
		} else if (ap.isJama()) {
			poljeAp.setIcon(new ImageIcon("slike/jama.png"));
			
		} else if (ap.isPotencijalnaJama() && ap.isPotencijalnoCudoviste() && ap.isPotencijalnoZlato()) {
			poljeAp.setIcon(new ImageIcon("slike/JCZ.png"));
			
		} else if (ap.isPotencijalnaJama() && ap.isPotencijalnoCudoviste() && !ap.isPotencijalnoZlato()) {
			poljeAp.setIcon(new ImageIcon("slike/JC.png"));
			
		} else if (ap.isPotencijalnaJama() && !ap.isPotencijalnoCudoviste() && ap.isPotencijalnoZlato()) {
			poljeAp.setIcon(new ImageIcon("slike/JZ.png"));
			
		} else if (ap.isPotencijalnaJama() && !ap.isPotencijalnoCudoviste() && !ap.isPotencijalnoZlato()) {
			poljeAp.setIcon(new ImageIcon("slike/J.png"));
			
		} else if (!ap.isPotencijalnaJama() && ap.isPotencijalnoCudoviste() && ap.isPotencijalnoZlato()) {
			poljeAp.setIcon(new ImageIcon("slike/CZ.png"));
			
		} else if (!ap.isPotencijalnaJama() && ap.isPotencijalnoCudoviste() && !ap.isPotencijalnoZlato()) {
			poljeAp.setIcon(new ImageIcon("slike/C.png"));
			
		} else if (!ap.isPotencijalnaJama() && !ap.isPotencijalnoCudoviste() && ap.isPotencijalnoZlato()) {
			poljeAp.setIcon(new ImageIcon("slike/Z.png"));
			
		} else {
			poljeAp.setIcon(new ImageIcon("slike/upitnik.png"));
			
		}
		
		SadrzajPolja sadrzaj = VisaSila.get().opisiPolje(x, y);
		switch (sadrzaj) {
		case CUDOVISTE:
			polje.setIcon(new ImageIcon("slike/cudoviste.png"));
			break;
			
		case JAMA:
			polje.setIcon(new ImageIcon("slike/jama.png"));
			break;
			
		case ZLATO:
			polje.setIcon(new ImageIcon("slike/zlato.png"));
			break;
			
		default:
			polje.setIcon(new ImageIcon("slike/tocka.png"));
			break;
		}
		
	}

//	/**
//	 * Pokretanje automatskog pomicanja agenta.
//	 */
//	private void start() {
//		if (svijetJeSpreman) {
//			// TODO: Vrti agenta...
//		}
//		
//	}
	
	/**
	 * Pomicanje agenta za jedan korak.
	 */
	private void korak() {
		VisaSila.get().getAgent().pomakniSe();
	}
	
//	/**
//	 * Pauziranje automatskog pomicanja agenta.
//	 */
//	private void pause() {
//		// TODO Auto-generated method stub
//		
//	}
	
	/**
	 * Pokretanje GUI-a.
	 */
	public static void open() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new PogledUSvijet().setVisible(true);
			}
		});
	}

	@Override
	public void interakcija(String informacija) {
		this.txtReport.setText(this.txtReport.getText() + "\n" + informacija);
	}

	@Override
	public void interakcija() {
		prikaziPromijenjeniSvijet();
		
	}

}
