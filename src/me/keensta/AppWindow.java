package me.keensta;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import me.keensta.UI.GameInfo;
import me.keensta.UI.Menu;
import me.keensta.UI.PawnEditting;
import me.keensta.UI.Resources;
import me.keensta.UI.World;
import me.keensta.util.AppPosition;
import me.keensta.util.Image;
import me.keensta.xmleditting.ClearBlood;
import me.keensta.xmleditting.ClearCorpses;
import me.keensta.xmleditting.ClearWeapons;
import me.keensta.xmleditting.ConvertRaiders;
import me.keensta.xmleditting.DataHandler;
import me.keensta.xmleditting.DeleteRaiders;
import me.keensta.xmleditting.DeleteRubbish;
import me.keensta.xmleditting.EditResources;

import org.jdom2.input.SAXBuilder;

import com.alee.extended.painter.DashedBorderPainter;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.rootpane.WebFrame;

public class AppWindow extends JPanel {

    private static final long serialVersionUID = 1L;

    private JFrame frame;

    // Gui stuff
    private WebMenuBar menuVar;
    private WebLabel borderLabel = new WebLabel();
    
    // Classes
    //TODO: Make sure all these classes use the AppPostion class.
    private Menu menu;
    private Image img = new Image();
    private GameInfo gameInfo;
    private Resources res;
    private World world;
    private PawnEditting pawnEdit;
    
    //Editing classes
    private ClearBlood cb;
    private ClearCorpses cc;
    private ClearWeapons cw;
    private ConvertRaiders cr;
    private DeleteRaiders draid;
    private DeleteRubbish dr;
    private EditResources er;
    
    // Xml Stuff
    private SAXBuilder builder = new SAXBuilder();
    private File xmlFile;
    private DataHandler dataHandler;

    public static void main(String[] args) {
        try {
            // Setting up WebLookAndFeel style
            UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
        } catch(Throwable e) {
            e.printStackTrace();
        }
        WebLookAndFeel.setDecorateFrames(true);

        WebFrame frame = new WebFrame("Rimworld Editor");
        frame.setShowMaximizeButton(false);
        frame.setResizable(false);
        frame.setRound(10);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new AppWindow(frame));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public AppWindow(JFrame frame) {
        this.frame = frame;

        // Load Needed Classes
        menu = new Menu(this);

        // BuildComponents
        menuVar = menu.createMenu(menuVar);

        // Adjust size and set layout and make border
        setPreferredSize(new Dimension(580, 350));
        setLayout(null);
        setBorder();
        
        // Add components
        add(menuVar);

        // Set component bounds (only needed by Absolute Positioning)
        menuVar.setBounds(AppPosition.MENUBAR_X, AppPosition.MENUBAR_Y, 580, 25);
    }

    //TODO: Move to menu class might be more fitting for this?
    @SuppressWarnings("rawtypes")
    private void setBorder() {
        DashedBorderPainter dbp = new DashedBorderPainter();
        dbp.setColor(Color.GRAY);
        dbp.setWidth(2);
        dbp.setDashPhase(3);
        borderLabel.setPainter(dbp);

        add(borderLabel);

        borderLabel.setBounds(0, 26, 580, 324);
    }

    public void makeVisible(int i) {
        menu.save.setEnabled(true);/*
        long startT = System.currentTimeMillis();*/
        
        if(i == 0) {
            gameInfo = new GameInfo(this);
            res = new Resources(this);
            world = new World(this);
            pawnEdit = new PawnEditting(this);
            
            gameInfo.BuildComponents();
            res.BuildComponents();
            world.BuildComponents();
            pawnEdit.BuildComponents();/*
            long endT = System.currentTimeMillis();
            System.out.println("Time to load in miliseconds " + (endT - startT));
            System.out.println("Time to load in seconds " + ((endT - startT) / 1000));*/
        } else {
            res.updateComponents();
        }
        
    }
    
    // Following code doesn't directly effect Gui.
    public void setXmlFile(File file) {
        xmlFile = file;

        menu.saveFileDir.setText(file.getAbsolutePath());
        dataHandler = new DataHandler(this, xmlFile, builder);
        
        intilizeClasses();
    }

    private void intilizeClasses() {
        cb = new ClearBlood(xmlFile, builder);
        cc = new ClearCorpses(xmlFile, builder);
        cw = new ClearWeapons(xmlFile, builder);
        cr = new ConvertRaiders(xmlFile, builder);
        draid = new DeleteRaiders(xmlFile, builder);
        dr = new DeleteRubbish(xmlFile, builder);
        er = new EditResources(xmlFile, builder);
    }

    public JFrame getFrame() {
        return frame;
    }

    public SAXBuilder getBuilder() {
        return builder;
    }

    public File getFile() {
        return xmlFile;
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }
    
    public Image getImage() {
        return img;
    }
    
    public Resources getRes() {
        return res;
    }
    
    public ClearBlood getClearBlood() {
        return cb;
    }
    
    public ClearCorpses getClearCorpses() {
        return cc;
    }
    
    public ClearWeapons getClearWeapons() {
        return cw;
    }
    
    public ConvertRaiders getConvertRaiders() {
        return cr;
    }
    
    public DeleteRaiders getDeleteRaiders() {
        return draid;
    }
    
    public DeleteRubbish getDeleteRubbish() {
        return dr;
    }
    
    public EditResources getEditResources() {
        return er;
    }
}