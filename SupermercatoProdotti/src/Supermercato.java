import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import Cramest.Prodotti.*;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.graphics.Point;

public class Supermercato {

	protected Shell shell;
	private static Text text_prezzo;
	private static Text text_codice;
	private static ListaSpesa carrello;
	private static Button btnNo;
	private static Button btnSi;
	private static List list;
	private static Prodotto[] inventario = {
			new Alimentare("4134", "Una Mela", 0.20, new Data(24, 1, 2016)),
			new Alimentare("4634", "Una Pera", 0.20, new Data(30, 1, 2016)),
			new Alimentare("4164", "Una Banana", 0.20, new Data(20, 1, 2016)),
			new NonAlimentare("4324", "Carta igenica", 1.00, "carta"),
			new NonAlimentare("6427", "Bottiglia d'acqua", 1.00, "plastica"),
			new NonAlimentare("9547", "Batteria", 1.00, "metallo pesante"),
			};
	private Text text_totale;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text text_scontrino;
	private boolean isUscito = false;
	
	public static void main(String[] args) {
		try {
			carrello = new ListaSpesa();
			Supermercato window = new Supermercato();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	private void mostraPrezzo(Prodotto prodotto){
		text_prezzo.setText(String.valueOf(prodotto.getPrezzo()));
		text_codice.setText(String.valueOf(prodotto.getCod()));
	}
	
	private void aggiungiProdotto(Prodotto prodotto){
		carrello.aggiungiProdotto(prodotto);
		aggiornaLista();
		text_totale.setText(String.valueOf(carrello.calcolaTOT()));
	}
	
	private void aggiornaLista(){
		String[] listaAggiornata = new String[carrello.size()];
		for(int i=0;i<carrello.size();i++){
			listaAggiornata[i] = carrello.getProdotto(i).toString();
		}
		list.setItems(listaAggiornata);
	}
	
	private void vaiAllaCassa(){
		shell.setSize(shell.getSize().x,400);
	}
	
	private void paga(boolean haTessera){
		if(!isUscito){
			shell.setSize(shell.getSize().x,450);
			if(haTessera)
				carrello.applicaSconti();
			text_scontrino.setText(String.valueOf(carrello.calcolaTOT()));
			isUscito = true;
		}
	}

	protected void createContents() {
		shell = new Shell();
		shell.setMinimumSize(new Point(460, 305));
		shell.setSize(460, 305);
		shell.setText("SWT Application");
		
		Label lblSupermercato = new Label(shell, SWT.CENTER);
		lblSupermercato.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 13, SWT.BOLD));
		lblSupermercato.setAlignment(SWT.CENTER);
		lblSupermercato.setBounds(0, 0, 434, 23);
		lblSupermercato.setText("SUPERMERCATO");
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.BORDER | SWT.V_SCROLL);
		scrolledComposite.setAlwaysShowScrollBars(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setTouchEnabled(true);
		scrolledComposite.setBounds(0, 29, 195, 203);
		
		list = new List(scrolledComposite, SWT.BORDER);
		scrolledComposite.setContent(list);
		scrolledComposite.setMinSize(list.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Button btnMela = new Button(shell, SWT.NONE);
		btnMela.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				mostraPrezzo(inventario[0]);
			}
		});
		btnMela.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				aggiungiProdotto(inventario[0]);
			}
		});
		btnMela.setBounds(201, 82, 75, 25);
		btnMela.setText("Mela");
		
		Button btnPera = new Button(shell, SWT.NONE);
		btnPera.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				mostraPrezzo(inventario[1]);
			}
		});
		btnPera.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				aggiungiProdotto(inventario[1]);
			}
		});
		btnPera.setText("Pera");
		btnPera.setBounds(282, 82, 75, 25);
		
		Button btnBanana = new Button(shell, SWT.NONE);
		btnBanana.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				mostraPrezzo(inventario[2]);
			}
		});
		btnBanana.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				aggiungiProdotto(inventario[2]);
			}
		});
		btnBanana.setText("Banana");
		btnBanana.setBounds(363, 82, 75, 25);
		
		Button btnCartaIgenica = new Button(shell, SWT.NONE);
		btnCartaIgenica.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				mostraPrezzo(inventario[3]);
			}
		});
		btnCartaIgenica.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				aggiungiProdotto(inventario[3]);
			}
		});
		btnCartaIgenica.setText("Carta Igenica");
		btnCartaIgenica.setBounds(201, 113, 75, 25);
		
		Button btnBottiglia = new Button(shell, SWT.NONE);
		btnBottiglia.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				mostraPrezzo(inventario[4]);
			}
		});
		btnBottiglia.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				aggiungiProdotto(inventario[4]);
			}
		});
		btnBottiglia.setText("Bottiglia");
		btnBottiglia.setBounds(282, 113, 75, 25);
		
		Button btnBatteria = new Button(shell, SWT.NONE);
		btnBatteria.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				mostraPrezzo(inventario[5]);
			}
		});
		btnBatteria.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				aggiungiProdotto(inventario[5]);
			}
		});
		btnBatteria.setText("Batteria");
		btnBatteria.setBounds(363, 113, 75, 25);
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(221, 26, 55, 21);
		lblNewLabel.setText("Prezzo");
		
		text_prezzo = new Text(shell, SWT.BORDER);
		text_prezzo.setEditable(false);
		text_prezzo.setBounds(348, 23, 76, 21);
		
		Label lblCodice = new Label(shell, SWT.NONE);
		lblCodice.setBounds(221, 53, 55, 15);
		lblCodice.setText("Codice");
		
		text_codice = new Text(shell, SWT.BORDER);
		text_codice.setEditable(false);
		text_codice.setBounds(348, 50, 76, 21);
		
		Button btnTotale = new Button(shell, SWT.NONE);
		btnTotale.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				vaiAllaCassa();
			}
		});
		btnTotale.setBounds(221, 207, 203, 25);
		btnTotale.setText("Vai alla cassa");
		
		Label lblTotale = new Label(shell, SWT.NONE);
		lblTotale.setBounds(10, 241, 55, 13);
		lblTotale.setText("Totale : ");
		
		text_totale = new Text(shell, SWT.BORDER);
		text_totale.setEditable(false);
		text_totale.setBounds(71, 238, 124, 21);
		
		btnNo = new Button(shell, SWT.NONE);
		btnNo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				paga(false);
			}
		});
		btnNo.setBounds(120, 332, 75, 25);
		formToolkit.adapt(btnNo, true, true);
		btnNo.setText("No");
		
		btnSi = new Button(shell, SWT.NONE);
		btnSi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				paga(true);
			}
		});
		btnSi.setBounds(282, 332, 75, 25);
		formToolkit.adapt(btnSi, true, true);
		btnSi.setText("Si");
		
		Label lblHaiLaCarta = new Label(shell, SWT.NONE);
		lblHaiLaCarta.setBounds(179, 300, 110, 15);
		formToolkit.adapt(lblHaiLaCarta, true, true);
		lblHaiLaCarta.setText("Hai la carta fedelt\u00E0?");
		
		Label lblAlloraFanno = new Label(shell, SWT.NONE);
		lblAlloraFanno.setBounds(120, 383, 75, 15);
		formToolkit.adapt(lblAlloraFanno, true, true);
		lblAlloraFanno.setText("Allora fanno :");
		
		text_scontrino = new Text(shell, SWT.BORDER);
		text_scontrino.setEditable(false);
		text_scontrino.setBounds(248, 380, 76, 21);
		formToolkit.adapt(text_scontrino, true, true);

	}
}
