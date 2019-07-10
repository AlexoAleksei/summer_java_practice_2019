package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import source.*;
import java.io.*;
import java.util.*;

public class Window extends JFrame {

    private JFileChooser fileChooser = null;
    private GraphViewPanel graphPanel;
    private JTextArea textArea;

    private boolean pressedVertexForDrag;
    private int indexVertexForDrag;

    private boolean pressedAddEdge;
    private boolean pressedVertexForEdge;
    private int indexVertexForEdge;

    private int conditionIterator = 0;

    public Window(){
        super("Practice project");

        // Frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setMinimumSize(Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(MAXIMIZED_BOTH);

        /*
        // MenuBar
        //JMenuBar menuBar = new JMenuBar();
        //menuBar.add(new JMenu("Справка"));
        //setJMenuBar(menuBar);
         */

        // Panels
        graphPanel = new GraphViewPanel();
        graphPanel.setBackground(Color.LIGHT_GRAY);
        JPanel toolBar = createPanel();
        toolBar.setPreferredSize(new Dimension(1000, 100));

        add(graphPanel);
        add(toolBar, BorderLayout.SOUTH);

        setVisible(true);

        // Mouse Listeners
        graphPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                indexVertexForDrag = -1;

                if(pressedAddEdge) {
                    if(pressedVertexForEdge == false) {
                        Vector<Vertex> vertices = graphPanel.getGraphDraw().getVertices();
                        for (int i = 0; i < vertices.size(); i++) {
                            if (Math.sqrt(Math.pow(e.getX() - vertices.elementAt(i).getVertexCenter().x, 2) + Math.pow(e.getY() - vertices.elementAt(i).getVertexCenter().y, 2))
                                    < (double) vertices.elementAt(i).getSide() / 2) {
                                indexVertexForEdge = i;
                                pressedVertexForEdge = true;
                                break;
                            }
                        }
                    }
                    else {
                        int current = -1;
                        Vector<Vertex> vertices = graphPanel.getGraphDraw().getVertices();
                        for (int i = 0; i < vertices.size(); i++) {
                            if (Math.sqrt(Math.pow(e.getX() - vertices.elementAt(i).getVertexCenter().x, 2) + Math.pow(e.getY() - vertices.elementAt(i).getVertexCenter().y, 2))
                                    < (double) vertices.elementAt(i).getSide() / 2) {
                                current = i;
                                break;
                            }
                        }

                        if(current > -1 && current != indexVertexForEdge) {
                            if(graphPanel.getGraph().getMatrix()[indexVertexForEdge][current] == 1){
                                indexVertexForEdge = -1;
                                pressedAddEdge = false;
                                pressedVertexForEdge = false;
                                return;
                            }

                            graphPanel.getGraphDraw().getEdges().add(new Edge(graphPanel.getGraphDraw().getVertices().elementAt(indexVertexForEdge),
                                    graphPanel.getGraphDraw().getVertices().elementAt(current)));
                            graphPanel.getGraph().addNewEdge(indexVertexForEdge, current);
                            graphPanel.getGraphDraw().removeBridges();
                            graphPanel.repaint(graphPanel.getVisibleRect());
                        }

                        indexVertexForEdge = -1;
                        pressedAddEdge = false;
                        pressedVertexForEdge = false;
                    }
                }
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

                if(indexVertexForDrag == -1) {
                    Vector<Vertex> vertices = graphPanel.getGraphDraw().getVertices();
                    for (int i = 0; i < vertices.size(); i++) {
                        if (Math.sqrt(Math.pow(e.getX() - vertices.elementAt(i).getVertexCenter().x, 2) + Math.pow(e.getY() - vertices.elementAt(i).getVertexCenter().y, 2))
                                < (double) vertices.elementAt(i).getSide() / 2) {
                            pressedVertexForDrag = true;
                            indexVertexForDrag = i;
                            break;
                        }
                    }
                }
                else {
                    pressedVertexForDrag = true;
                }

                if(pressedVertexForDrag){
                    graphPanel.getGraphDraw().getVertices().elementAt(indexVertexForDrag).setVertexCenter(new Point(e.getX(), e.getY()));
                }

                pressedVertexForDrag = false;
                //indexVertexForDrag = -1;
                graphPanel.repaint(graphPanel.getVisibleRect());
            }
        });
    }

    private JPanel createPanel(){
        // Buttons
        final JButton buttonAddVertex = new JButton("Добавить вершину");
        buttonAddVertex.addActionListener(new ButtonAddVertex());

        final JButton buttonAddEdge = new JButton("Добавить ребро");
        buttonAddEdge.addActionListener(new ButtonAddEdge());

        final JButton buttonFirstCondition = new JButton("Begin");
        buttonFirstCondition.addActionListener(new ButtonFirstCondition());
        buttonFirstCondition.setEnabled(false);

        final JButton buttonPrevCondition = new JButton("Prev");
        buttonPrevCondition.addActionListener(new ButtonPrevCondition());
        buttonPrevCondition.setEnabled(false);

        final JButton buttonNextCondition = new JButton("Next");
        buttonNextCondition.addActionListener(new ButtonNextCondition());
        buttonNextCondition.setEnabled(false);

        final JButton buttonEndCondition = new JButton("End");
        buttonEndCondition.addActionListener(new ButtonEndCondition());
        buttonEndCondition.setEnabled(false);

        JButton buttonReadFile = new JButton("Считать из файла");
        //buttonReadFile.addActionListener(new ButtonFileRead());
        buttonReadFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    graphPanel.setGraph(new BridgeFinder(readFile()));
                }
                catch (NullPointerException ex){
                    textArea.append("Файл не считан.\n");
                    return;
                }

                buttonAddVertex.setEnabled(true);
                buttonAddEdge.setEnabled(true);
                buttonFirstCondition.setEnabled(false);
                buttonPrevCondition.setEnabled(false);
                buttonNextCondition.setEnabled(false);
                buttonEndCondition.setEnabled(false);

                //для того, чтобы при инициализированном графе, когда вызывается кнопка "считать файл" и закрывается,
                //а затем вызыввался поиск мостов, не падала программа
                graphPanel.getGraphDraw().getVertices().clear();
                graphPanel.getGraph().getConditionList().clear();

                graphPanel.getGraphDraw().setGraph(graphPanel.getGraph());
                textArea.append("Файл успешно считан.\n");
                graphPanel.repaint(graphPanel.getVisibleRect());
            }
        });

        JButton buttonFindBridge = new JButton("Поиск мостов");
        buttonFindBridge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(graphPanel.getGraph() == null){
                    textArea.append("Граф не инициализирован.\n");
                    return;
                }

                buttonAddVertex.setEnabled(false);
                buttonAddEdge.setEnabled(false);
                buttonFirstCondition.setEnabled(true);
                buttonPrevCondition.setEnabled(true);
                buttonNextCondition.setEnabled(true);
                buttonEndCondition.setEnabled(true);

                graphPanel.getGraphDraw().removeBridges();
                graphPanel.getGraph().startFind();
                graphPanel.getGraph().printBridgesToTextAre(textArea);

                conditionIterator = 0;
                graphPanel.getGraphDraw().setCondition(graphPanel.getGraph().getConditionList().elementAt(conditionIterator));

                graphPanel.repaint(graphPanel.getVisibleRect());
            }
        });

        // Text Area
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        JScrollPane log = new JScrollPane(textArea);
        log.setPreferredSize(new Dimension(300,80));

        // Tool Bar
        JPanel toolBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(buttonReadFile);
        toolBar.add(buttonFindBridge);
        toolBar.add(log);
        toolBar.add(buttonAddVertex);
        toolBar.add(buttonAddEdge);
        toolBar.add(buttonFirstCondition);
        toolBar.add(buttonPrevCondition);
        toolBar.add(buttonNextCondition);
        toolBar.add(buttonEndCondition);
        toolBar.setBorder(BorderFactory.createRaisedBevelBorder());
        return toolBar;
    }

    /*
    //кнопка "Считать с файла"
    public class ButtonFileRead implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                graphPanel.setGraph(new BridgeFinder(readFile()));
            }
            catch (NullPointerException ex){
                textArea.append("Файл не считан.\n");
                return;
            }

            //для того, чтобы при инициализированном графе, когда вызывается кнопка "считать файл" и закрывается,
            //а затем вызыввался поиск мостов, не падала программа
            graphPanel.getGraphDraw().getVertices().clear();
            graphPanel.getGraph().getConditionList().clear();

            graphPanel.getGraphDraw().setGraph(graphPanel.getGraph());
            textArea.append("Файл успешно считан.\n");
            graphPanel.repaint(graphPanel.getVisibleRect());
        }
    }

     */

    /*
    //кнопка "Поиск мостов"
    public class ButtonFindBridge implements ActionListener {
        public void actionPerformed(ActionEvent e){

            if(graphPanel.getGraph() == null){
                textArea.append("Граф не инициализирован.\n");
                return;
            }
            graphPanel.getGraphDraw().removeBridges();
            graphPanel.getGraph().startFind();
            graphPanel.getGraph().printBridgesToTextAre(textArea);

            conditionIterator = 0;
            graphPanel.getGraphDraw().setCondition(graphPanel.getGraph().getConditionList().elementAt(conditionIterator));

            graphPanel.repaint(graphPanel.getVisibleRect());
        }
    }
     */

    //кнопка "Добавить вершину"
    public class ButtonAddVertex implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(graphPanel.getGraph() == null || graphPanel.getGraphDraw() == null) {
                graphPanel.setGraph(new BridgeFinder(new int[0][0]));
                graphPanel.getGraphDraw().setGraph(graphPanel.getGraph());
            }

            //добавляем в вектор вершин для рисования вершину с центром в середине окна
            graphPanel.getGraphDraw().getVertices().add(new Vertex(new Point(graphPanel.getVisibleRect().width/2, graphPanel.getVisibleRect().height/2),
                    50));

            //добавляем вершину в vertexList и matrix текущего графа графа
            graphPanel.getGraph().addNewVertex();

            graphPanel.repaint(graphPanel.getVisibleRect());
        }
    }

    //кнопка "Добавить ребро"
    public class ButtonAddEdge implements ActionListener {
        public void actionPerformed(ActionEvent e){
            pressedAddEdge = true;
        }
    }

    //кнопка "Начальное состояние"
    public class ButtonFirstCondition implements ActionListener {
        public void actionPerformed(ActionEvent e){
            graphPanel.getGraphDraw().clearBridges();
            conditionIterator = 0;
            graphPanel.getGraphDraw().setCondition(graphPanel.getGraph().getConditionList().elementAt(conditionIterator));
            graphPanel.repaint(graphPanel.getVisibleRect());
        }
    }

    //кнопка "Следующее состояние"
    public class ButtonNextCondition implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(graphPanel.getGraph().getConditionList().size()-1 > conditionIterator) {
                graphPanel.getGraphDraw().setCondition(graphPanel.getGraph().getConditionList().elementAt(++conditionIterator));
            }
            else {
                textArea.append("Достигнуто конечное состояние алгоритма\n");
            }
            graphPanel.repaint(graphPanel.getVisibleRect());
        }
    }

    //кнопка "Предыдущее состояние"
    public class ButtonPrevCondition implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(conditionIterator > 0) {
                graphPanel.getGraphDraw().clearBridges();
                graphPanel.getGraphDraw().setCondition(graphPanel.getGraph().getConditionList().elementAt(--conditionIterator));
            }
            else {
                textArea.append("Достигнуто конечное состояние алгоритма\n");
            }
            graphPanel.repaint(graphPanel.getVisibleRect());
        }
    }

    //кнопка "Конечное состояние"
    public class ButtonEndCondition implements ActionListener {
        public void actionPerformed(ActionEvent e){
            conditionIterator = graphPanel.getGraph().getConditionList().size() - 1;
            graphPanel.getGraphDraw().setCondition(graphPanel.getGraph().getConditionList().elementAt(conditionIterator));
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
        localization();
        int[][] matrix = new int[0][0];
        fileChooser.setDialogTitle("Выбор Файла");
        // Определение режима - только файл
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(Window.this);
        // Создаем наш файл
        File newfile;
        try {
            newfile = new File(fileChooser.getSelectedFile().toString());
        }
        catch (NullPointerException e){
            throw e;
        }
        if(newfile.exists())
        {
            try {
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
                    for(number = 0; number < ary.length; number++) {
                        if(number + 1 < ary.length) {
                            if ((ary[number + 1] == ' ')) {
                                matrix[count][numbervertex] = Character.getNumericValue(ary[number]);
                                numbervertex++;
                                number++;
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(Window.this, "Ошибка, неверные данные");
                                System.exit(0);
                            }
                        }
                        if(number + 1 == ary.length){
                            matrix[count][numbervertex] = Character.getNumericValue(ary[number]);
                            count ++;
                            numbervertex = 0;
                        }
                    }
                }
            }catch (IOException q){
                textArea.append("Файл не считан.\n");
            }
        }
        return matrix;
    }
}
