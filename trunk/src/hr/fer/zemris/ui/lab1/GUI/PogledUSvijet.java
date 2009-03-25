package hr.fer.zemris.ui.lab1.GUI;

import hr.fer.zemris.ui.lab1.CentralnaInformacijskaAgencija;
import hr.fer.zemris.ui.lab1.SadrzajPolja;
import hr.fer.zemris.ui.lab1.VisaSila;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
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

public class PogledUSvijet extends JFrame implements IChangeListener {
	private static final long serialVersionUID = 1L;
	
	private JTextField txtSirinaSvijeta;
	private JTextField txtVisinaSvijeta;
	private JTextField txtPCudovista;
	private JTextField txtPJama;
	private JTextPane txtReport;
	private JPanel agentovPogled;
	
	public PogledUSvijet() {
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	CentralnaInformacijskaAgencija.getCIA().ukloniPretplatu(PogledUSvijet.this);
                dispose();
            }
        });
		initGUI();
		setTitle("Ultimativno rješenje za traženje zlata i svih zlatnih proizvoda");
		CentralnaInformacijskaAgencija.getCIA().pretplataNaPoruke(this);
		CentralnaInformacijskaAgencija.getCIA().pretplataNaStanjeSvijeta(this);
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
        this.setSize(800, 600);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		this.getContentPane().setLayout(new BorderLayout());
		
		// POCETAK: Gornja traka
		JPanel gornjiDio = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));

		JButton start = new JButton(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		start.setText("Pokreni potragu");
		gornjiDio.add(start);
		
		JButton korak = new JButton(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				korak();
			}
		});
		korak.setText("Napravi korak");
		gornjiDio.add(korak);
		
		JButton pause = new JButton(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pause();
			}
		});
		pause.setText("Pauza");
		gornjiDio.add(pause);
		
		JButton newWorld = new JButton(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newWorld();
			}
		});
		newWorld.setText("Novi svijet");
		gornjiDio.add(newWorld);
		this.getContentPane().add(gornjiDio, BorderLayout.NORTH);
		// KRAJ: Gornja traka
		
		// POCETAK: Desni izbornik (parametri)
		JPanel parametri = new JPanel(new GridLayout(3, 1, 2, 5));
		JPanel velicinaParam = new JPanel(new GridLayout(2, 1, 0, 2));
		this.txtSirinaSvijeta = new JTextField();
		this.txtSirinaSvijeta.setPreferredSize(new Dimension(100, 15));
		velicinaParam.add(txtSirinaSvijeta);
		this.txtVisinaSvijeta = new JTextField();
		this.txtVisinaSvijeta.setPreferredSize(new Dimension(100, 15));
		velicinaParam.add(txtVisinaSvijeta);
		velicinaParam.setPreferredSize((new Dimension(150, 0)));
		velicinaParam.setBorder(BorderFactory.createTitledBorder("Veličina svijeta"));
		parametri.add(velicinaParam);
		
		JPanel vjerojatnostiParam = new JPanel(new GridLayout(2, 1, 0, 2));
		vjerojatnostiParam.setBorder(BorderFactory.createTitledBorder("Vjerojatnosti pojava"));
		this.txtPCudovista = new JTextField();
		this.txtPCudovista.setPreferredSize(new Dimension(100, 15));
		vjerojatnostiParam.add(txtPCudovista);
		this.txtPJama = new JTextField();
		this.txtPJama.setPreferredSize(new Dimension(100, 15));
		vjerojatnostiParam.add(txtPJama);
		velicinaParam.setPreferredSize((new Dimension(150, 0)));
		parametri.add(vjerojatnostiParam);
		this.getContentPane().add(parametri, BorderLayout.WEST);
		// KRAJ: Desni izbornik (parametri)
		
		// POCETAK: Sredina (agentov pogled)
		
		prikaziNoviSvijet();
		this.getContentPane().add(this.agentovPogled, BorderLayout.CENTER);
		// KRAJ: Sredina (agentov pogled)
		
		// POCETAK: Desni dio (stvarni svijet i baza znanja)
		// KRAJ: Desni dio (stvarni svijet i baza znanja)
		
		// POCETAK: Donji dio, ispis rezultata.
		JPanel donjiDio = new JPanel(new BorderLayout());
		donjiDio.setPreferredSize(new Dimension(400, 100));
		this.txtReport = new JTextPane();
		this.txtReport.setEditable(false);
		JScrollPane reportScroll = new JScrollPane(this.txtReport);
		reportScroll.setBorder(BorderFactory.createTitledBorder("Ispis"));
		donjiDio.add(reportScroll, BorderLayout.CENTER);
		this.getContentPane().add(donjiDio, BorderLayout.SOUTH);
		// KRAJ: Donji dio, ispis rezultata.
	}
	
	private void prikaziNoviSvijet() {
		int apSirina = VisaSila.get().getSvijet().getVelicinaSvijeta().width;
		int apVisina = VisaSila.get().getSvijet().getVelicinaSvijeta().height;
		this.agentovPogled = new JPanel(new GridLayout(apVisina, apSirina, 0, 0));
		Point agentPoz = VisaSila.get().getAgent().getPozicija();
		
		for (int y = 1; y <= apVisina; y++) {
			for (int x = 1; x <= apSirina; x++) {
				if (x == agentPoz.x && y == agentPoz.y) {
					this.agentovPogled.add(new JLabel(new ImageIcon("slike/agent.png")));
					continue;
				}
				SadrzajPolja sadrzaj = VisaSila.get().opisiPolje(x, y);
				switch (sadrzaj) {
				case CUDOVISTE:
					this.agentovPogled.add(new JLabel(new ImageIcon("slike/cudoviste.png")));
					break;
					
				case JAMA:
					this.agentovPogled.add(new JLabel(new ImageIcon("slike/jama.png")));
					break;
					
				case ZLATO:
					this.agentovPogled.add(new JLabel(new ImageIcon("slike/zlato.png")));
					break;
					
				default:
					this.agentovPogled.add(new JLabel(new ImageIcon("slike/upitnik.png")));
					break;
				}
				
			}
		}
	}
	
	private void prikaziPromijenjeniSvijet() {
		int apSirina = VisaSila.get().getSvijet().getVelicinaSvijeta().width;
		int apVisina = VisaSila.get().getSvijet().getVelicinaSvijeta().height;
		Point agentPoz = VisaSila.get().getAgent().getPozicija();
		
		Component[] comps = this.agentovPogled.getComponents();
		for (int y = 1; y <= apVisina; y++) {
			for (int x = 1; x <= apSirina; x++) {
				JLabel polje = (JLabel) comps[(y-1)*apSirina+(x-1)];
				
				if (x == agentPoz.x && y == agentPoz.y) {
					polje.setIcon(new ImageIcon("slike/agent.png"));
					continue;
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
					polje.setIcon(new ImageIcon("slike/upitnik.png"));
					break;
				}
				
			}
		}
		this.agentovPogled.repaint();
	}

	private void start() {
		// TODO Auto-generated method stub
		
	}
	
	private void korak() {
		VisaSila.get().getAgent().pomakniSe();
	}
	
	private void pause() {
		// TODO Auto-generated method stub
		
	}
	
	private void newWorld() {
		// TODO Auto-generated method stub
		
	}
	
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
		interakcija(VisaSila.get().getAgent().getPozicija().toString());
		prikaziPromijenjeniSvijet();
		//this.agentovPogled.getComponentAt(x, y)
		
	}

}
