/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Linux
 */
public class main extends javax.swing.JFrame {

	private int A, B, C, D;
	private float puntosCuadranteI[][] = new float[520][2];
	private float puntosCuadranteII[][] = new float[520][2];
	private float incrementoX = 0.01f;

	public main() {
		initComponents();
		this.setLocationRelativeTo(null);
		this.setSize(800, 600);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//Dibuja el eje Y
		int ejeYi = Plano.getWidth() / 2;
		int ejeYf = Plano.getHeight();
		g.drawLine(ejeYi, 0, ejeYi, ejeYf);
//		System.out.println(ejeYi+",0 - "+ejeYi+","+ejeYf);

		//Dibuja el eje X
		int ejeXi = Plano.getHeight() / 2;
		int ejeXf = Plano.getWidth();
		g.drawLine(0, ejeXi, ejeXf, ejeXi);
//		System.out.println("0,"+ejeXi+" - "+ejeXf+","+ejeXi);

		//Dibujamos un punto en el origen del plano, restanto el tamaño del punto
		g.setColor(Color.black);
		g.fillOval(ejeYi - 2, ejeXi - 2, 5, 5);

		//coordenadas exactas donde se ubica el origen
		int origenX = ejeYi;
		int origenY = ejeXi;

//		System.out.println("x = "+origenX);
//		System.out.println("y = "+origenY);
		//partir  de 5 en 5 cada eje
		// eje X
		int cx = origenX;
		int cy = origenY;
		//Negativo X
		int longitudLineaCorte = 5;
		for (int i = 0; i < 5; i++) {
			cx -= 53;
			//dibuja las lineas de corte
			g.drawLine(cx, cy - longitudLineaCorte, cx, cy + longitudLineaCorte);
			//dibuja los nuemeros en los cortes
			g.drawString("-" + (i + 1) + "", cx - 7, (cy + 20));
		}
		//Positivo X
		cx = origenX;
		for (int i = 0; i < 5; i++) {
			cx += 53;
			g.drawLine(cx, cy - longitudLineaCorte, cx, cy + longitudLineaCorte);
			g.drawString((i + 1) + "", cx - 3, cy + 20);
		}
		//Eje Y
		//Positivo Y
		cx = origenX;
		longitudLineaCorte = 5;
		for (int i = 0; i < 5; i++) {
			cy -= 53;
			g.drawLine(cx - longitudLineaCorte, cy, cx + longitudLineaCorte, cy);
			g.drawString((i + 1) + "", cx + 10, cy + 5);
		}
		//Negativo Y
		cx = origenX;
		cy = origenY;
		longitudLineaCorte = 5;
		for (int i = 0; i < 5; i++) {
			cy += 53;
			g.drawLine(cx - longitudLineaCorte, cy, cx + longitudLineaCorte, cy);
			g.drawString("-" + (i + 1) + "", cx + 10, cy + 4);
		}
		obtenerValoresSliders();
		inicializarPuntosCuadrante();
		CalcularYs();
		GraficarFuncion(g);
	}

	public void GraficarFuncion(Graphics g) {
		int xi = 0;
		int yi = 0;
		int xf = 0;
		int yf = 0;
		Graphics2D g2d = (Graphics2D) g;
		for (int i = puntosCuadranteI.length - 1; i > 0; i--) {
			xi = (int) ((int) 269 + ConvertirAMiEscala(puntosCuadranteI[i][0]));
			yi = (int) ((int) 299 - ConvertirAMiEscala(puntosCuadranteI[i][1]));
//			System.out.println(i+"= "+ConvertirAMiEscala(puntosCuadranteI[i][0], false)+","+ConvertirAMiEscala(puntosCuadranteI[i][1], true));

			xf = (int) ((int) 269 + ConvertirAMiEscala(puntosCuadranteI[i - 1][0]));
			yf = (int) ((int) 299 - ConvertirAMiEscala(puntosCuadranteI[i - 1][1]));
			g2d.setColor(Color.blue);
			//para el grosor de la linea
			g2d.setStroke(new BasicStroke(2));
			//se dibuja la onda
			g2d.drawLine(xi, yi, xf, yf);
//			System.out.println("xi = "+xi+",yi = "+yi);
//			System.out.println("xf = "+xf+", yf = "+yf);
//			g.fillOval(xi, yi, 2, 2);
//			g.fillOval(xf, yf, 2, 2);
		}
		for (int i = 0; i < puntosCuadranteII.length - 1; i++) {
			xi = (int) (269 + ConvertirAMiEscala(puntosCuadranteII[i][0]));
			yi = (int) (299 - ConvertirAMiEscala(puntosCuadranteII[i][1]));

			xf = (int) (269 + ConvertirAMiEscala(puntosCuadranteII[i + 1][0]));
			yf = (int) (299 - ConvertirAMiEscala(puntosCuadranteII[i + 1][1]));
                        
			g2d.drawLine(xi, yi, xf, yf);
//			g.fillOval(xi, yi, 2, 2);
//			g.fillOval(xf, yf, 2, 2);
		}
	}

	public float ConvertirAMiEscala(float valor) {
		return valor * 53;
	}

	public void inicializarPuntosCuadrante() {
		float decremento = 0;
		//Cuadrante I
		for (int i = 0; i < puntosCuadranteI.length; i++) {
			puntosCuadranteI[i][0] = decremento;
			decremento -= incrementoX;

		}
		//Cuadrante II
		decremento = 0;
		for (int i = 0; i < puntosCuadranteII.length; i++) {
			puntosCuadranteII[i][0] = decremento;
			decremento += incrementoX;
		}

	}

	public void CalcularYs() {
		//Cuadrante I
		//System.out.println("Cuadrante I");
		for (int i = 0; i < puntosCuadranteI.length; i++) {
			puntosCuadranteI[i][1] = (float) (A * Math.sin(B * puntosCuadranteI[i][0] + C) + D);
//			System.out.println(i + " = " + puntosCuadranteI[i][0] + "," + puntosCuadranteI[i][1]);
		}
		for (int i = 0; i < puntosCuadranteII.length; i++) {
			puntosCuadranteII[i][1] = (float) (A * Math.sin(B * puntosCuadranteII[i][0] + C) + D);
//			System.out.println(i + " = " + puntosCuadranteII[i][0] + "," + puntosCuadranteII[i][1]);
		}
	}

	public void obtenerValoresSliders() {
		A = slAmplitud.getValue();
		B = slPeriodo.getValue();
		C = slDesface.getValue();
		D = slConstante.getValue();
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Contenedor = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        slAmplitud = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        slPeriodo = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        slDesface = new javax.swing.JSlider();
        jLabel4 = new javax.swing.JLabel();
        slConstante = new javax.swing.JSlider();
        jLabel5 = new javax.swing.JLabel();
        Plano = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Características de una onda");
        setMinimumSize(new java.awt.Dimension(800, 600));
        setResizable(false);

        Contenedor.setDividerLocation(540);
        Contenedor.setEnabled(false);
        Contenedor.setMinimumSize(new java.awt.Dimension(800, 600));
        Contenedor.setPreferredSize(new java.awt.Dimension(800, 600));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new java.awt.GridLayout(8, 1, 0, 10));

        jLabel1.setText("Amplitud:");
        jPanel1.add(jLabel1);

        slAmplitud.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        slAmplitud.setMajorTickSpacing(1);
        slAmplitud.setMaximum(6);
        slAmplitud.setMinorTickSpacing(1);
        slAmplitud.setPaintTicks(true);
        slAmplitud.setSnapToTicks(true);
        slAmplitud.setValue(1);
        slAmplitud.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slAmplitudStateChanged(evt);
            }
        });
        jPanel1.add(slAmplitud);

        jLabel2.setText("Periodo:");
        jPanel1.add(jLabel2);

        slPeriodo.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        slPeriodo.setMajorTickSpacing(1);
        slPeriodo.setMaximum(6);
        slPeriodo.setMinorTickSpacing(1);
        slPeriodo.setValue(1);
        slPeriodo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slPeriodoStateChanged(evt);
            }
        });
        jPanel1.add(slPeriodo);

        jLabel3.setText("Desface:");
        jPanel1.add(jLabel3);

        slDesface.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        slDesface.setMajorTickSpacing(1);
        slDesface.setMaximum(5);
        slDesface.setMinimum(-5);
        slDesface.setMinorTickSpacing(1);
        slDesface.setValue(1);
        slDesface.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slDesfaceStateChanged(evt);
            }
        });
        jPanel1.add(slDesface);

        jLabel4.setText("Constante:");
        jPanel1.add(jLabel4);

        slConstante.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        slConstante.setMajorTickSpacing(1);
        slConstante.setMaximum(5);
        slConstante.setMinimum(-5);
        slConstante.setMinorTickSpacing(1);
        slConstante.setPaintTicks(true);
        slConstante.setValue(1);
        slConstante.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slConstanteStateChanged(evt);
            }
        });
        jPanel1.add(slConstante);

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 230, 400));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Parámetros");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 210, -1));

        Contenedor.setRightComponent(jPanel4);

        Plano.setBackground(new java.awt.Color(255, 255, 255));
        Plano.setMinimumSize(new java.awt.Dimension(279, 540));
        Plano.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PlanoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout PlanoLayout = new javax.swing.GroupLayout(Plano);
        Plano.setLayout(PlanoLayout);
        PlanoLayout.setHorizontalGroup(
            PlanoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        PlanoLayout.setVerticalGroup(
            PlanoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        Contenedor.setLeftComponent(Plano);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Contenedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Contenedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void slAmplitudStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slAmplitudStateChanged
		obtenerValoresSliders();
		GraficarFuncion(this.getGraphics());
		this.repaint();
    }//GEN-LAST:event_slAmplitudStateChanged

    private void slPeriodoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slPeriodoStateChanged
		obtenerValoresSliders();
		GraficarFuncion(this.getGraphics());
		this.repaint();
    }//GEN-LAST:event_slPeriodoStateChanged

    private void slDesfaceStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slDesfaceStateChanged
		obtenerValoresSliders();
		GraficarFuncion(this.getGraphics());
		this.repaint();
    }//GEN-LAST:event_slDesfaceStateChanged

    private void slConstanteStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slConstanteStateChanged
		obtenerValoresSliders();
		GraficarFuncion(this.getGraphics());
		this.repaint();
    }//GEN-LAST:event_slConstanteStateChanged

    private void PlanoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlanoMouseClicked
        int x = evt.getX();
        int y = evt.getY();
        
        System.out.println("x: "+x);
        System.out.println("y: "+y);
        
        
        
        
    }//GEN-LAST:event_PlanoMouseClicked

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new main().setVisible(true);

			}
		});
	}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSplitPane Contenedor;
    private javax.swing.JPanel Plano;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSlider slAmplitud;
    private javax.swing.JSlider slConstante;
    private javax.swing.JSlider slDesface;
    private javax.swing.JSlider slPeriodo;
    // End of variables declaration//GEN-END:variables
}
