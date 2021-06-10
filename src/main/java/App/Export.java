package App;

import java.awt.*;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Export extends Thread {
    private DefaultTableModel modelTable;
    private JFrame windApp;
    int id, res;
    String addres;

    public Export(DefaultTableModel tab, JFrame jf, Pharmacy apteka) {
        this.modelTable = tab;
        this.windApp = jf;
        this.id = apteka.getId();
        this.addres = apteka.getAddress();
        this.res = apteka.calcAllSold();
    }

    public void run() {
        synchronized (modelTable)// тк общий ресурс для нескольких потоков
        {
            // Блок который подлежит синхронизации
            try {
                SimpleDateFormat formatForDateNow1 = new SimpleDateFormat("HH-mm-ss dd.MM.yyyy");//изменяем формат вывода данных, тк нельзя использовать ":"
                SimpleDateFormat formatForDateNow2 = new SimpleDateFormat("HH:mm dd.MM.yyyy");
                String[] columns = {"Id", "Название", "Болезнь", "Количество", "Продано", "Цена"};
                String path = ".\\Reports\\PDFreport from " + formatForDateNow1.format(new Date()) + ".pdf";

                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(path));
                document.open();

                Font fontHead = FontFactory.getFont(".\\fonts\\DejaVuSans.ttf", "cp1251", BaseFont.EMBEDDED, 10, Font.BOLD);
                Font font = FontFactory.getFont(".\\fonts\\DejaVuSans.ttf", "cp1251", BaseFont.EMBEDDED, 10);

                Paragraph title = new Paragraph("Таблица товаров. Аптека id:" + id + "  Адрес: " + addres + "\nДата создания отчета: " + formatForDateNow2.format(new Date()) + "\n\n", font);
                document.add(title);

                PdfPTable tbl = new PdfPTable(modelTable.getColumnCount());
                for (int i = 0; i < columns.length; ++i) {
                    Phrase tx = new Phrase(columns[i], fontHead);
                    PdfPCell cell = new PdfPCell(tx);
                    cell.setBackgroundColor(new BaseColor(Color.lightGray.getRGB()));
                    tbl.addCell(cell);
                }

                for (int i = 0; i < modelTable.getRowCount(); i++)
                    for (int j = 0; j < modelTable.getColumnCount(); j++)
                        tbl.addCell(new Phrase(modelTable.getValueAt(i, j).toString(), font));
                document.add(tbl);

                Paragraph totalSales = new Paragraph("Всего было продано товаров на сумму: " + res + " рублей.", font);
                document.add(totalSales);

                document.close();
                JOptionPane.showMessageDialog(windApp, "Готово. Создан PDF-отчет (папка Reports)");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(windApp, e.toString());
            }
        }
    }
}

