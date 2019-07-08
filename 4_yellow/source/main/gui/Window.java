package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

//import com.sun.deploy.panel.ControlPanel;
import source.*;
import draw.Drawing;
import java.io.*;

public class Window extends JFrame {

    private JFileChooser fileChooser = null;
    private GraphViewPanel graphPanel;
    private JTextArea textArea;

    private boolean pressedOnVertex;
    private int indexPressedVertex;

    public Window(){
        super("Practice project");


        // Frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setMinimumSize(Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(MAXIMIZED_BOTH);

        // MenuBar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JMenu("Справка"));
        setJMenuBar(menuBar);

        // Panels
        graphPanel = new GraphViewPanel();
        graphPanel.setBackground(Color.LIGHT_GRAY);
        JPanel toolBar = createToolBar();
        toolBar.setPreferredSize(new Dimension(1000, 100));

        add(graphPanel);
        add(toolBar, BorderLayout.SOUTH);

        setVisible(true);

        // Mouse Listeners
        graphPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);


            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);


            }
        });

        graphPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                if(graphPanel.getGraphDraw().getVertices() == null)
                    return;

                Vertex[] vertices = graphPanel.getGraphDraw().getVertices();
                for(int i = 0; i < vertices.length; i++){
                    if(Math.sqrt(Math.pow(e.getX() - vertices[i].getVertexCenter().x, 2) + Math.pow(e.getY() - vertices[i].getVertexCenter().y,2))
                            < (double)vertices[i].getSide()/2){
                        //textArea.append("Попадание в вершину!\n");
                        pressedOnVertex = true;
                        indexPressedVertex = i;
                        break;
                    }
                }

                if(pressedOnVertex){
                    graphPanel.getGraphDraw().getVertices()[indexPressedVertex].setVertexCenter(new Point(e.getX(), e.getY()));
                }

                graphPanel.repaint(graphPanel.getVisibleRect());
            }
        });
    }

    private JPanel createToolBar(){
        // Buttons
        JButton buttonCreateGraph = new JButton("Построить граф");
        buttonCreateGraph.addActionListener(new ButtonGraphCreator());

        JButton buttonReadFile = new JButton("Считать из файла");
        buttonReadFile.addActionListener(new ButtonFileRead());

        JButton buttonFindBridge = new JButton("Поиск мостов");
        buttonFindBridge.addActionListener(new ButtonFindBridge());

        // Text Area
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        JScrollPane log = new JScrollPane(textArea);
        log.setPreferredSize(new Dimension(300,80));

        // Tool Bar
        JPanel toolBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(buttonCreateGraph);
        toolBar.add(buttonReadFile);
        toolBar.add(buttonFindBridge);
        toolBar.add(log);
        toolBar.setBorder(BorderFactory.createRaisedBevelBorder());
        return toolBar;
    }

    //кнопка "Считать с файла"
    public class ButtonFileRead implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try{
                graphPanel.setGraph(new BridgeFinder(readFile()));
            }catch (NullPointerException k)
            {
                textArea.append("Файл не выбран.\n");
                return;

            }
            graphPanel.getGraphDraw().setGraph(graphPanel.getGraph());
            graphPanel.repaint(graphPanel.getVisibleRect());
        }
    }

    //кнопка "СОЗДАТЬ ГРАФ"
    public class ButtonGraphCreator implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //JPanel frame = new Drawing();
            System.out.println("Оп, граф показал тебе.");
        }
    }

    //кнопка "Поиск мостов"
    public class ButtonFindBridge implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(graphPanel.getGraph() == null){
                textArea.append("Граф не инициализирован.\n");
                return;
            }
            graphPanel.getGraph().startFind();
            graphPanel.getGraph().printBridgesToTextAre(textArea);
            graphPanel.getGraphDraw().setBridges();
            graphPanel.repaint(graphPanel.getVisibleRect());
        }
    }

    private void localization(){
        UIManager.put(
                "FileChooser.openButtonText", "Открыть");
        UIManager.put(
                "FileChooser.cancelButtonText", "Отмена");
        UIManager.put(
                "FileChooser.fileNameLabelText", "Наименование файла");
        UIManager.put(
                "FileChooser.filesOfTypeLabelText", "Типы файлов");
        UIManager.put(
                "FileChooser.lookInLabelText", "Директория");
        UIManager.put(
                "FileChooser.saveInLabelText", "Сохранить в директории");
        UIManager.put(
                "FileChooser.folderNameLabelText", "Путь директории");
        fileChooser = new JFileChooser();
    }

    private int[][] readFile(){
        localization(); //для считывания файлов

        int[][] matrix = new int[0][0];
        try {
        fileChooser.setDialogTitle("Выбор Файла");
        // Определение режима - только файл
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(Window.this);
        // Создаем наш файл

            File newfile = new File(fileChooser.getSelectedFile().toString());
            if (newfile.exists() == true) {

                int count = 0;
                int i = 0;
                int number = 0;
                int numbervertex = 0;
                int k = 0;

                FileInputStream fstream = new FileInputStream(newfile);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String strLine;
                if ((strLine = br.readLine()) != null) {
                    i = Integer.parseInt(strLine);
                    matrix = new int[i][i];
                }
                while ((strLine = br.readLine()) != null) {
                    char[] ary = strLine.toCharArray();
                    for (number = 0; number < ary.length; number++) {
                        if (number + 1 < ary.length) {
                            if ((ary[number + 1] == ' ')) {
                                matrix[count][numbervertex] = Character.getNumericValue(ary[number]);
                                numbervertex++;
                                number++;
                            } else {
                                JOptionPane.showMessageDialog(Window.this, "Ошибка, неверные данные");
                                System.exit(0);
                            }
                        }
                        if (number + 1 == ary.length) {
                            matrix[count][numbervertex] = Character.getNumericValue(ary[number]);
                            count++;
                            numbervertex = 0;
                        }
                    }
                }
                textArea.append("Файл успешно считан.\n");
            }
        }catch (IOException q){
            textArea.append("Ошибка.\n");
            }
        catch(NullPointerException e){
            throw e;
        }

        return matrix;
    }
}
