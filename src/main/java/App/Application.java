package App;
import java.awt.*;
import java.awt.event.*;
import javax.persistence.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.log4j.Logger;

public class Application {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Database persistence");
    private EntityManager em = emf.createEntityManager();
    final static Logger logger = Logger.getLogger(Application.class);
    private Pharmacy Apteka;

    private JFrame windowApp;

    private JToolBar toolBar;
    private DefaultTableModel model;
    private DefaultTableModel modelSold;
    private JButton open;
    private JButton add;
    private JButton delete;
    private JButton buy;
    private JButton calc;
    private JButton edit;
    private JButton export;
    private JButton sales;
    private JScrollPane scroll = new JScrollPane();
    private JTable preparatTable;
    private JTable preparatTableSold;
    private JComboBox kind;
    private JComboBox selectorTable;
    private JTextField searchName;
    private JButton filter;
    private JDialog dialog;
    private  JPanel filterPanel;

    private int buttonHeight = 20;
    private int buttonWidth = 20;

    public void show() {//открытие приложения
        // / Создание окна
        windowApp = new JFrame("Pharmacy management");
        windowApp.setSize(700, 480);
        windowApp.setLocation(100, 100);
        windowApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создание кнопок, прикрепление иконок и масштабирование их картинок
        open = new JButton(new ImageIcon(new ImageIcon("pic/openDB.png").getImage().getScaledInstance(buttonWidth, buttonHeight, java.awt.Image.SCALE_SMOOTH)));
        add  = new JButton(new ImageIcon(new ImageIcon("pic/add.png").getImage().getScaledInstance(buttonWidth, buttonHeight, java.awt.Image.SCALE_SMOOTH)));
        delete = new JButton(new ImageIcon(new ImageIcon("pic/delete.png").getImage().getScaledInstance(buttonWidth, buttonHeight, java.awt.Image.SCALE_SMOOTH)));
        buy = new JButton(new ImageIcon(new ImageIcon("pic/buy.png").getImage().getScaledInstance(buttonWidth, buttonHeight, java.awt.Image.SCALE_SMOOTH)));
        calc = new JButton(new ImageIcon(new ImageIcon("pic/calc.png").getImage().getScaledInstance(buttonWidth, buttonHeight, java.awt.Image.SCALE_SMOOTH)));
        edit = new JButton(new ImageIcon(new ImageIcon("pic/edit.png").getImage().getScaledInstance(buttonWidth, buttonHeight, java.awt.Image.SCALE_SMOOTH)));
        export = new JButton(new ImageIcon(new ImageIcon("pic/export.png").getImage().getScaledInstance(buttonWidth, buttonHeight, java.awt.Image.SCALE_SMOOTH)));
        sales = new JButton(new ImageIcon(new ImageIcon("pic/sales.png").getImage().getScaledInstance(buttonWidth, buttonHeight, java.awt.Image.SCALE_SMOOTH)));
        open.setActionCommand("open");
        add.setActionCommand("add");
        delete.setActionCommand("delete");
        buy.setActionCommand("buy");
        edit.setActionCommand("edit");
        calc.setActionCommand("calc");
        export.setActionCommand("export");
        sales.setActionCommand("sales");
// Настройка подсказок для кнопок
        open.setToolTipText("Открыть БД аптеки");
        add.setToolTipText("Добавить запись");
        delete.setToolTipText("Удалить запись");
        buy.setToolTipText("Покупка товара");
        calc.setToolTipText("Посчитать доход");
        edit.setToolTipText("Изменить запись");
        export.setToolTipText("Экспортировать таблицу");
        sales.setToolTipText("Список продаж");
// Добавление кнопок на панель инструментов
        toolBar = new JToolBar("Панель инструментов");
        toolBar.add(open);
        toolBar.add(add);
        toolBar.add(delete);
        toolBar.add(edit);
        toolBar.add(buy);
        toolBar.add(calc);
        toolBar.add(export);
        toolBar.add(sales);
// Размещение панели инструментов
        windowApp.setLayout(new BorderLayout());
        windowApp.add(toolBar, BorderLayout.NORTH);
// Подготовка компонентов нижней панели
        kind = new JComboBox(new String[]{"Препарат", "Болезнь"});
        searchName = new JTextField("", 20);
        filter = new JButton("Поиск");
        selectorTable = new JComboBox(new String[]{"Товары в аптеке", "Проданные товары"});
// Добавление компонентов на панель
        filterPanel = new JPanel();
        filterPanel.add(kind);
        filterPanel.add(searchName);
        filterPanel.add(filter);
// Размещение панели поиска внизу окна
        windowApp.add(filterPanel, BorderLayout.SOUTH);

        // Визуализация экранной формы
        windowApp.setVisible(true);
        logger.info("The App is running");

        //Слушатель для всех кнопок
        ActionListener actionPressButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (e.getActionCommand()){
                    case("open"):
                        logger.info("loading database");
                        openFunc();
                        logger.info("loading database id " + Apteka.getId() + " completed");
                        break;
                    case("add"):
                        try {
                            checkActiveDB();//при неудаче создается исключение
                        }
                        catch (OpenException ex){
                            JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), ex.getMessage());
                            break;
                        }
                        if (checkRightTable())
                            addFunc();
                        break;
                    case("delete"):
                        try {
                            checkActiveDB();
                        }
                        catch (OpenException ex){
                            JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), ex.getMessage());
                            break;
                        }
                        if (checkRightTable() && checkSelectRow())
                            deleteFunc();
                        break;
                    case("edit"):
                        try {
                            checkActiveDB();
                        }
                        catch (OpenException ex){
                            JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), ex.getMessage());
                            break;
                        }
                        if (checkRightTable() && checkSelectRow())
                            editFunc();
                        break;
                    case("buy"):
                        try {
                            checkActiveDB();
                        }
                        catch (OpenException ex){
                            JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), ex.getMessage());
                            break;
                        }
                        if (checkRightTable() && checkSelectRow())
                            buyFunc();
                        break;
                    case("calc"):
                        try {
                            checkActiveDB();
                        }
                        catch (OpenException ex){
                            JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), ex.getMessage());
                            break;
                        }
                        calcFunc();
                        break;
                    case("export"):
                        try {
                            checkActiveDB();
                        }
                        catch (OpenException ex){
                            JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), ex.getMessage());
                            break;
                        }

                        Export export = new Export(model, windowApp, Apteka);
                        export.start();
                        break;
                    case("sales"):
                        try {
                            checkActiveDB();
                        }
                        catch (OpenException ex){
                            JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), ex.getMessage());
                            break;
                        }
                        generateSalesFunc();
                        break;
                    case("Поиск"):
                        try {
                            checkActiveDB();
                        }
                        catch (OpenException ex){
                            JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), ex.getMessage());
                            break;
                        }
                        if (!checkRightTable())
                            break;
                        if (searchName.getText().equals(""))
                        {
                            JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), "<html>Введите название");
                            break;
                        }
                        searchFunc();
                        break;
                }
            }
        };

        //слушатель для селектора таблиц
        selectorTable.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    try {
                        checkActiveDB();
                    }
                    catch (OpenException ex){
                        JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), ex.getMessage());
                        return;
                    }

                    if (selectorTable.getSelectedItem() == "Товары в аптеке")
                    {
                        scroll.setViewportView(preparatTable);
                        windowApp.add(scroll, BorderLayout.CENTER);
                    }
                    else{
                        if (preparatTableSold == null)
                            fillSoldTable();
                        else
                        {
                            scroll.setViewportView(preparatTableSold);
                            windowApp.add(scroll, BorderLayout.CENTER);
                        }
                    }
                }
            }
        });
        open.addActionListener(actionPressButton);
        add.addActionListener(actionPressButton);
        delete.addActionListener(actionPressButton);
        buy.addActionListener(actionPressButton);
        calc.addActionListener(actionPressButton);
        edit.addActionListener(actionPressButton);
        export.addActionListener(actionPressButton);
        sales.addActionListener(actionPressButton);
        filter.addActionListener(actionPressButton);
    }

    private JPanel createAddPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 15, 27));
        panel.add(new JLabel("Название препарата: "));
        panel.add(new JTextField("введите название",45));
        panel.add(new JLabel("Болезнь: "));
        panel.add(new JTextField("введите болезнь",45));
        panel.add(new JLabel("Цена: "));
        panel.add(new JTextField( "0",45));
        panel.add(new JLabel("Количество: "));
        panel.add(new JTextField("0",45));
        return panel;
    }

    private JPanel createEditPanel(Preparat pr){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 15, 27));
        panel.add(new JLabel("Название препарата: "));
        panel.add(new JTextField(pr.getName(),45));
        panel.add(new JLabel("Болезнь: "));
        panel.add(new JTextField(pr.getIllness().getName(),45));
        panel.add(new JLabel("Цена: "));
        panel.add(new JTextField( Integer.toString(pr.getPrice()),45));
        panel.add(new JLabel("Количество: "));
        panel.add(new JTextField(Integer.toString(pr.getCount()),45));
        panel.add(new JLabel("Проданное количество: "));
        panel.add(new JTextField(Integer.toString(pr.getCntSold()),45));
        return panel;
    }

    private void addNote(String name, String ill, String price_, String cnt_) throws PharmFieldsException {
        int price, cnt;
        try{
             price = Integer.parseInt(price_);
             cnt = Integer.parseInt(cnt_);
        }
        catch (NumberFormatException | NullPointerException nfe){
            throw new PharmFieldsException();//вызов ошибки добавления препарата
        }
        if ((name.equals("введите название")) ||(ill.equals("введите болезнь"))||(price <= 0)||(cnt <= 0))
            throw new PharmFieldsException();//вызов ошибки добавления препарата
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();

        //проверка на существование болезни
        String querystr = "SELECT i FROM Illness i WHERE i.name like '" + ill + "'";//создание запроса
        List<Illness> query = em.createQuery(querystr).getResultList();
        Illness ill_search;
        if (query.size() != 0)//если нашли болезнь
            ill_search = query.get(0);
        else//создаём болезнь
        {
            ill_search = new Illness(ill);
            em.persist(ill_search);
        }

        Preparat pr = new Preparat(ill_search, name, price, cnt);

        Apteka.addPreparat(pr);
        em.persist(pr);
        em.persist(Apteka);
        em.getTransaction().commit();
        model.addRow(new Object[]{ pr.getId(), name, ill, cnt, 0, price});
        return;
    }

    private void editNote(Preparat pr, String name, String ill, String price_, String cnt_, String cntSold_) throws PharmFieldsException {
        int price, cnt, cntSold;
        try{
            price = Integer.parseInt(price_);
            cnt = Integer.parseInt(cnt_);
            cntSold = Integer.parseInt(cntSold_);
            if ((price <= 0)||(cnt < 0)||(cntSold < 0))
                throw new NumberFormatException();
        }
        catch (NumberFormatException | NullPointerException nfe){
            throw new PharmFieldsException();//вызов ошибки изменения препарата
        }
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();

        //проверка на существование болезни
        String querystr = "SELECT i FROM Illness i WHERE i.name like '" + ill + "'";//создание запроса
        List<Illness> query = em.createQuery(querystr).getResultList();
        Illness ill_search;
        if (query.size() != 0)//если нашли болезнь
            ill_search = query.get(0);
        else//создаём болезнь
        {
            ill_search = new Illness(ill);
            em.persist(ill_search);
        }

        pr.setName(name);
        pr.setIllness(ill_search);
        pr.setCntSold(cntSold);
        pr.setCount(cnt);
        pr.setPrice(price);
        em.persist(pr);
        em.getTransaction().commit();

        preparatTable.setValueAt(pr.getCount(), preparatTable.getSelectedRow(), 3);//изменяем значения в ячейках
        preparatTable.setValueAt(pr.getCntSold(), preparatTable.getSelectedRow(), 4);
        preparatTable.setValueAt(pr.getName(), preparatTable.getSelectedRow(), 1);
        preparatTable.setValueAt(pr.getIllness().getName(), preparatTable.getSelectedRow(), 2);
        preparatTable.setValueAt(pr.getPrice(), preparatTable.getSelectedRow(), 5);
        model.fireTableCellUpdated(preparatTable.getSelectedRow(), 3);//обновляем ячейки
        model.fireTableCellUpdated(preparatTable.getSelectedRow(), 4);
        model.fireTableCellUpdated(preparatTable.getSelectedRow(), 1);
        model.fireTableCellUpdated(preparatTable.getSelectedRow(), 2);
        model.fireTableCellUpdated(preparatTable.getSelectedRow(), 5);
        return;
    }

    private void checkActiveDB() throws OpenException {
        if (Apteka == null)//если не открыта аптека
            throw new OpenException();
    }

    private boolean checkRightTable(){
        if (selectorTable.getSelectedItem().toString() != "Товары в аптеке")
        {
            JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), "<html>Выберите таблицу товаров");
            return false;
        }
        return true;
    }

    private boolean checkSelectRow(){//проверяет выделена ли одна строка
        if (preparatTable.getSelectedRowCount() != 1)
        {
            JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), "<html>Выберите одну строку в таблице");
            return false;
        }
        return true;
    }

    private void fillTable(){
        TypedQuery<Preparat> query = em.createQuery(
                "SELECT p FROM Preparat p WHERE p.pharmacy.id = :id", Preparat.class);
        List<Preparat> preparats = query.setParameter("id", Apteka.getId()).getResultList();
        String [][] data = new String[preparats.size()][6];
        for (int i = 0; i < preparats.size(); i++) {
            data[i][0] = Integer.toString(preparats.get(i).getId());
            data[i][1] = preparats.get(i).getName();
            data[i][2] = preparats.get(i).getIllness().getName();
            data[i][3] = Integer.toString(preparats.get(i).getCount());
            data[i][4] = Integer.toString(preparats.get(i).getCntSold());
            data[i][5] = Integer.toString(preparats.get(i).getPrice());
        }
        String [] columns = {"Id", "Название", "Болезнь", "Количество", "Продано", "Цена"};
       model= new DefaultTableModel(data, columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        preparatTable.setModel(model);
        scroll.setViewportView(preparatTable);
        windowApp.add(scroll, BorderLayout.CENTER);
    }

    private void fillSoldTable(){
        preparatTableSold = new JTable();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        TypedQuery<SoldPreparat> query = em.createQuery(
                "SELECT s FROM SoldPreparat s WHERE s.pharmacy.id = :id", SoldPreparat.class);
        List<SoldPreparat> sldPreparats = query.setParameter("id", Apteka.getId()).getResultList();
        String [][] data = new String[sldPreparats.size()][7];
        for (int i = 0; i < sldPreparats.size(); i++) {
            data[i][0] = Integer.toString(sldPreparats.get(i).getId());
            data[i][1] = Integer.toString(sldPreparats.get(i).getPreparat().getId());
            data[i][2] = sldPreparats.get(i).getName();
            data[i][3] = sldPreparats.get(i).getIllness().getName();
            data[i][4] = Integer.toString(sldPreparats.get(i).getCntSold());
            data[i][5] = Integer.toString(sldPreparats.get(i).getPrice());
            data[i][6] = formatForDateNow.format(sldPreparats.get(i).getDate());
        }
        String [] columns = {"Id","Id препарата", "Название", "Болезнь", "Продано", "Цена", "Время продажи"};
        modelSold= new DefaultTableModel(data, columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        preparatTableSold.setModel(modelSold);
        scroll.setViewportView(preparatTableSold);
        windowApp.add(scroll, BorderLayout.CENTER);
    }

    private void addFunc(){
        JPanel addWin = createAddPanel();
        dialog = new JDialog(windowApp, "Добавление записи", true);
        //нажатие "добавить"
        JButton addBtn = new JButton("Добавить");
        ActionListener actionPressAdd = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component[] components = addWin.getComponents();
                try{
                    addNote(((JTextField)components[1]).getText(), ((JTextField)components[3]).getText(), ((JTextField)components[5]).getText(), ((JTextField)components[7]).getText());
                    JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), "<html>Препарат добавлен");
                    dialog.dispose();
                }
                catch (PharmFieldsException aex){
                    logger.error("an attempt to enter incorrect fields when adding a preparat");
                    JOptionPane.showMessageDialog(new JDialog(windowApp, "", true),aex.getMessage());
                }
            }
        };
        addBtn.addActionListener(actionPressAdd);

        dialog.setResizable(false);//окно нельзя изменять в размере
        dialog.setPreferredSize(new Dimension(300, 250));
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setLocation(250, 250);
        dialog.add(addWin, BorderLayout.NORTH);
        dialog.add(addBtn, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void deleteFunc(){
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
        Preparat prd = em.find(Preparat.class, Integer.parseInt(preparatTable.getValueAt(preparatTable.getSelectedRow(), 0).toString()));
        Illness il = em.find(Illness.class, prd.getIllness().getId());
        Apteka.deletePreparat(prd);
        il.deletePreparat(prd);
        prd.executePr();
        em.remove(prd);
        em.getTransaction().commit();
        model.removeRow(preparatTable.getSelectedRow());
        model.fireTableDataChanged();
    }

    private void editFunc(){
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
        Preparat pre = em.find(Preparat.class, Integer.parseInt(preparatTable.getValueAt(preparatTable.getSelectedRow(), 0).toString()));
        JPanel editWin = createEditPanel(pre);
        dialog = new JDialog(windowApp, "Изменить информацию о товаре", true);
        JButton editBtn = new JButton("Изменить информацию");
        ActionListener actionPressEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Component[] components = editWin.getComponents();
                try{
                    editNote( pre, ((JTextField)components[1]).getText(), ((JTextField)components[3]).getText(), ((JTextField)components[5]).getText(), ((JTextField)components[7]).getText(), ((JTextField)components[9]).getText());
                    JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), "<html>Информация изменена");
                    dialog.dispose();
                }
                catch (PharmFieldsException aex){
                    logger.error("an attempt to enter incorrect fields when editing a preparat");
                    JOptionPane.showMessageDialog(new JDialog(windowApp, "", true),aex.getMessage());
                }
            }
        };
        editBtn.addActionListener(actionPressEdit);
        dialog.setResizable(false);//окно нельзя изменять в размере
        dialog.setPreferredSize(new Dimension(300, 300));
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setLocation(250, 250);
        dialog.add(editBtn, BorderLayout.SOUTH);
        dialog.add(editWin, BorderLayout.NORTH);
        dialog.pack();
        dialog.setVisible(true);
        em.persist(pre);
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.getTransaction().commit();
        preparatTable.setValueAt(pre.getCount(), preparatTable.getSelectedRow(), 3);//изменяем значения в ячейках
        preparatTable.setValueAt(pre.getCntSold(), preparatTable.getSelectedRow(), 4);
        model.fireTableCellUpdated(preparatTable.getSelectedRow(), 3);//обновляем ячейки
        model.fireTableCellUpdated(preparatTable.getSelectedRow(), 4);
    }

    private void buyFunc(){
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
        Preparat pr = em.find(Preparat.class, Integer.parseInt(preparatTable.getValueAt(preparatTable.getSelectedRow(), 0).toString()));
        dialog = new JDialog(windowApp, "Покупка", true);
        JButton buyBtn = new JButton("Приобрести товар");
        JTextField buyTxt = new JTextField("1");
        ActionListener actionPressBuy = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int c = Integer.parseInt(buyTxt.getText());
                    if (c<0)
                        JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), "<html>Ошибка! Число должно быть положительным");
                    else
                    {
                        if (c > pr.getCount())
                            JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), String.format("<html>В наличии только %d единиц", pr.getCount()));
                        else
                        {
                            SoldPreparat sldPr = pr.buyPreparat(c);
                            em.persist(pr);
                            em.persist(sldPr);
                            em.getTransaction().commit();
                            preparatTable.setValueAt(pr.getCount(), preparatTable.getSelectedRow(), 3);//изменяем значения в ячейках
                            preparatTable.setValueAt(pr.getCntSold(), preparatTable.getSelectedRow(), 4);
                            model.fireTableCellUpdated(preparatTable.getSelectedRow(), 3);//обновляем ячейки
                            model.fireTableCellUpdated(preparatTable.getSelectedRow(), 4);
                            if (preparatTableSold != null)
                                modelSold.addRow(new Object[]{ sldPr.getId(), pr.getId(), sldPr.getName(), sldPr.getIllness().getName(), sldPr.getCntSold(), sldPr.getPrice(), new SimpleDateFormat("HH:mm dd.MM.yyyy").format(sldPr.getDate())});
                            dialog.dispose();
                        }
                    }
                } catch (NumberFormatException | NullPointerException nfe) {
                    JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), "<html>Ошибка! Нужно ввести число");
                }
            }
        };
        buyBtn.addActionListener(actionPressBuy);
        dialog.setResizable(false);//окно нельзя изменять в размере
        dialog.setPreferredSize(new Dimension(250, 115));
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setLocation(250, 250);
        dialog.add(new JLabel("Введите количество товара: "), BorderLayout.NORTH);
        dialog.add(buyTxt, BorderLayout.CENTER);
        dialog.add(buyBtn, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void searchFunc(){
        String a =(String) kind.getSelectedItem();
        if(a.equals("Препарат"))
        {
            int b = Apteka.findPreparat(searchName.getText());
            if (b != -1)
                preparatTable.setRowSelectionInterval(b, b);//выделяем строку с таким препаратом
            else
            {
                preparatTable.clearSelection();
                JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), "<html>Такого препарата нет");
            }
        }
        if(a.equals("Болезнь"))
        {
            List<Preparat> rightPrep = Apteka.findListDrugs(searchName.getText());
            if (rightPrep.size() == 0)
            {
                preparatTable.clearSelection();
                JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), "<html>Таких препаратов нет");
            }
            else
            {
                String str = "Подходящие препараты: ";
                for (int i=0; i < rightPrep.size(); ++i)
                    str += "\n" +  rightPrep.get(i).getId() + " " + rightPrep.get(i).getName();
                JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), str);
            }

        }
    }

    private void openFunc(){
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
        preparatTableSold = null;
        preparatTable = new JTable();
        dialog = new JDialog(windowApp, "Открытие БД", true);
        JButton openBtn = new JButton("Открыть БД");
        JButton addPharmBtn = new JButton("Добавить новую БД аптеки");
        JTextField adresPharm = new JTextField("Введите адрес новой аптеки", 20);
        List<Pharmacy> listPharmacy = em.createQuery("SELECT p FROM Pharmacy p").getResultList();
        String [][] data = new String[listPharmacy.size()][2];
        for (int i = 0; i < listPharmacy.size(); i++) {
            data[i][0] = Integer.toString(listPharmacy.get(i).getId());
            data[i][1] = listPharmacy.get(i).getAddress();
        }
        String [] columns = {"Id", "Адрес"};
        DefaultTableModel modelPh = new DefaultTableModel(data, columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable pharmTable = new JTable(modelPh);
        JScrollPane pane = new JScrollPane(pharmTable);
        JPanel addPharmPanel = new JPanel();
        addPharmPanel.setLayout(new GridLayout(0, 1, 1, 1));
        addPharmPanel.add(adresPharm);
        addPharmPanel.add(addPharmBtn);
        addPharmPanel.add(openBtn);

        ActionListener actionPressOpen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pharmTable.getSelectedRow() == -1 || pharmTable.getSelectedRowCount() != 1)//если не выбрана строчка
                    JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), "<html>Выберите одну строку в таблице");
                else
                {
                    Apteka = em.find(Pharmacy.class, Integer.parseInt(pharmTable.getValueAt(pharmTable.getSelectedRow(), 0).toString()));
                    dialog.dispose();
                    fillTable();//открываем таблицу
                    filterPanel.add(selectorTable);
                    selectorTable.setSelectedIndex(0);
                    windowApp.add(filterPanel, BorderLayout.SOUTH);
                    windowApp.setVisible(true);
                }
            }
        };
        ActionListener actionPressAddPharm = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (adresPharm.getText().equals("Введите адрес новой аптеки"))//если не выбрана строчка
                    JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), "<html>Введите адрес аптеки");
                else
                {
                    Apteka = new Pharmacy(adresPharm.getText());
                    em.persist(Apteka);
                    dialog.dispose();
                    em.getTransaction().commit();
                    fillTable();//открываем таблицу
                    filterPanel.add(selectorTable);
                    windowApp.add(filterPanel, BorderLayout.SOUTH);
                    windowApp.setVisible(true);
                }
            }
        };
        openBtn.addActionListener(actionPressOpen);
        addPharmBtn.addActionListener(actionPressAddPharm);
        dialog.add(pane, BorderLayout.CENTER);
        dialog.add(addPharmPanel, BorderLayout.SOUTH);
        dialog.setResizable(false);//окно нельзя изменять в размере
        dialog.setPreferredSize(new Dimension(300, 250));
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setLocation(250, 250);
        dialog.pack();
        dialog.setVisible(true);
    }
    private void calcFunc(){
        int res=0;
        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();

        dialog = new JDialog(windowApp, "Посчитать доход", true);
        JButton defDateBtn = new JButton("Посчитать за последний месяц");
        JButton dateBtn = new JButton("Посчитать за указанный период");
        JPanel panel = createDatePanel();

        dateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            int df, mf, yf, ds, ms, ys;
                Component[] components = panel.getComponents();
                try{
                    try
                    {
                        df = Integer.parseInt(((JTextField)components[1]).getText());
                        mf = Integer.parseInt(((JTextField)components[2]).getText());
                        yf = Integer.parseInt(((JTextField)components[3]).getText());
                        ds = Integer.parseInt(((JTextField)components[5]).getText());
                        ms = Integer.parseInt(((JTextField)components[6]).getText());
                        ys = Integer.parseInt(((JTextField)components[7]).getText());
                    }
                    catch (NumberFormatException | NullPointerException nfe){
                        throw new PharmFieldsException();}

                    a.set(yf, mf-1, df, 0, 0);
                    b.set(ys, ms-1, ds, 23, 59);
                    if ((df <= 0)||(mf <= 0)||(yf<=0)||(ds <= 0)||(ms <= 0)||(ys<=0)||(a.getTimeInMillis() > b.getTimeInMillis()))
                        throw new PharmFieldsException();
                    dialog.dispose();
                }
                catch ( PharmFieldsException aex){
                    JOptionPane.showMessageDialog(new JDialog(windowApp, "", true),"Введите все числовые поля корректно");
                }
            }
        });
        defDateBtn.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                a.set(b.get(Calendar.YEAR), b.get(Calendar.MONTH)-1, b.get(Calendar.DAY_OF_MONTH));
                dialog.dispose();
            }
        }));
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(0, 1, 1, 1));
        btnPanel.add(dateBtn, BorderLayout.NORTH);
        btnPanel.add(defDateBtn, BorderLayout.SOUTH);
        dialog.add(panel, BorderLayout.NORTH);
        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setResizable(false);//окно нельзя изменять в размере
        dialog.setPreferredSize(new Dimension(420, 180));
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setLocation(250, 250);
        dialog.pack();
        dialog.setVisible(true);
        if (preparatTableSold == null)
        {
            fillSoldTable();
            scroll.setViewportView(preparatTable);
            windowApp.add(scroll, BorderLayout.CENTER);
        }
        TypedQuery<SoldPreparat> query = em.createQuery(
                "SELECT s FROM SoldPreparat s WHERE s.date >=:fd AND s.date <= :sd AND s.pharmacy.id = :id", SoldPreparat.class);
        query.setParameter("fd", a.getTime());
        query.setParameter("sd", b.getTime());
        query.setParameter("id", Apteka.getId());
        List<SoldPreparat> sldPreparats = query.getResultList();
        for (int i=0; i<sldPreparats.size(); ++i)
            res += sldPreparats.get(i).getCntSold()*sldPreparats.get(i).getPrice();
        JOptionPane.showMessageDialog(new JDialog(windowApp, "", true), String.format("<html>Доход: %d рублей", res));
    }

    private JPanel createDatePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 4, 1, 10));
        panel.add(new JLabel("С какого числа: "));
        panel.add(new JTextField("День",45));
        panel.add(new JTextField("Месяц",45));
        panel.add(new JTextField("Год",45));
        panel.add(new JLabel("По какое число: "));
        panel.add(new JTextField("День",45));
        panel.add(new JTextField("Месяц",45));
        panel.add(new JTextField("Год",45));
        return panel;
    }

    private void generateSalesFunc(){
        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();
        dialog = new JDialog(windowApp, "Показать продажи", true);
        JButton dateBtn = new JButton("Показать за указанный период");
        JPanel panel = createDatePanel();

        dateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int df, mf, yf, ds, ms, ys;
                Component[] components = panel.getComponents();
                try{
                    try
                    {
                        df = Integer.parseInt(((JTextField)components[1]).getText());
                        mf = Integer.parseInt(((JTextField)components[2]).getText());
                        yf = Integer.parseInt(((JTextField)components[3]).getText());
                        ds = Integer.parseInt(((JTextField)components[5]).getText());
                        ms = Integer.parseInt(((JTextField)components[6]).getText());
                        ys = Integer.parseInt(((JTextField)components[7]).getText());
                    }
                    catch (NumberFormatException | NullPointerException nfe){
                        throw new PharmFieldsException();}
                    a.set(yf, mf-1, df, 0, 0);
                    b.set(ys, ms-1, ds, 23, 59);
                    if ((df <= 0)||(mf <= 0)||(yf<=0)||(ds <= 0)||(ms <= 0)||(ys<=0)||(a.getTimeInMillis() > b.getTimeInMillis()))
                        throw new PharmFieldsException();
                    dialog.dispose();
                }
                catch ( PharmFieldsException aex){
                    JOptionPane.showMessageDialog(new JDialog(windowApp, "", true),"Введите все числовые поля корректно");
                }
            }
        });

        dialog.add(panel, BorderLayout.NORTH);
        dialog.add(dateBtn, BorderLayout.SOUTH);
        dialog.setResizable(false);//окно нельзя изменять в размере
        dialog.setPreferredSize(new Dimension(420, 180));
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setLocation(250, 250);
        dialog.pack();
        dialog.setVisible(true);

        if (preparatTableSold == null)
        {
            fillSoldTable();
            scroll.setViewportView(preparatTable);
            windowApp.add(scroll, BorderLayout.CENTER);
        }
        TypedQuery<SoldPreparat> query = em.createQuery(
                "SELECT s FROM SoldPreparat s WHERE s.date >=:fd AND s.date <= :sd AND s.pharmacy.id = :id", SoldPreparat.class);
        query.setParameter("fd", a.getTime());
        query.setParameter("sd", b.getTime());
        query.setParameter("id", Apteka.getId());
        List<SoldPreparat> sldPreparats = query.getResultList();
        dialog = new JDialog(windowApp, "Проданные товары за период с " + new SimpleDateFormat("dd.MM.yyyy").format(a.getTime()) + " по " + new SimpleDateFormat("dd.MM.yyyy").format(b.getTime()), false);

        String [][] data = new String[sldPreparats.size()][4];
        List<SoldPreparat> uniqueSldPreparats = new ArrayList<SoldPreparat>();
        boolean check = false;
        int k=0;
        for (int i=0; i< sldPreparats.size(); ++i) {
            for (int j = 0; j < uniqueSldPreparats.size(); ++j)
                if (uniqueSldPreparats.get(j).getPreparat().getId() == sldPreparats.get(i).getPreparat().getId())
                {
                    check = true;
                    k=j;
                    j=uniqueSldPreparats.size();
                }
            if (check == false) {
                data[uniqueSldPreparats.size()][0] = Integer.toString(sldPreparats.get(i).getPreparat().getId());
                data[uniqueSldPreparats.size()][1] = sldPreparats.get(i).getName();
                data[uniqueSldPreparats.size()][2] = sldPreparats.get(i).getIllness().getName();
                data[uniqueSldPreparats.size()][3] = Integer.toString(sldPreparats.get(i).getCntSold());
                uniqueSldPreparats.add(sldPreparats.get(i));
            }
            else{
                data[k][3] = Integer.toString(Integer.parseInt(data[k][3]) + sldPreparats.get(i).getCntSold());
                check = false;
            }
        }
        String [][] data1 = new String[uniqueSldPreparats.size()][4];
        for (int i = 0; i < uniqueSldPreparats.size(); ++i) {
            data1[i][0] = data[i][0];
            data1[i][1] = data[i][1];
            data1[i][2] = data[i][2];
            data1[i][3] = data[i][3];
        }
        String [] columns = {"Id", "Название", "Болезнь", "Количество"};
        DefaultTableModel modelSales = new DefaultTableModel(data1, columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable pharmSales = new JTable(modelSales);
        JScrollPane pane = new JScrollPane(pharmSales);

        dialog.add(pane, BorderLayout.CENTER);
        dialog.setResizable(false);//окно нельзя изменять в размере
        dialog.setPreferredSize(new Dimension(420, 360));
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setLocation(250, 250);
        dialog.pack();
        dialog.setVisible(true);
    }
}


