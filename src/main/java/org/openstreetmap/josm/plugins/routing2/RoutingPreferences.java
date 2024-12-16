// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.plugins.routing2;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.util.EnumSet;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.openstreetmap.josm.gui.preferences.ExtensibleTabPreferenceSetting;
import org.openstreetmap.josm.gui.preferences.PreferenceTabbedPane;
import org.openstreetmap.josm.gui.widgets.JosmComboBox;
import org.openstreetmap.josm.gui.widgets.JosmComboBoxModel;
import org.openstreetmap.josm.plugins.routing2.lib.valhalla.TransportationType;
import org.openstreetmap.josm.plugins.routing2.lib.valhalla.ValhallaConfig;
import org.openstreetmap.josm.tools.ImageProvider;

/**
 * Preferences for the routing engine(s)
 */
public class RoutingPreferences extends ExtensibleTabPreferenceSetting {
    private ValhallaConfig valhallaConfig;

    RoutingPreferences() {
        super("dialogs/routing", tr("Routing2"), tr("Various settings that influence the routing plugin."), false);
        this.valhallaConfig = PreferenceHelper.readRecord("routing2.valhalla.config", ValhallaConfig.DEFAULT);
    }

    @Override
    public boolean ok() {
        PreferenceHelper.writeRecord("routing2.valhalla.config",
                ValhallaConfig.DEFAULT.equals(valhallaConfig) ? null : valhallaConfig);
        return false;
    }

    @Override
    public ImageIcon getIcon(ImageProvider.ImageSizes size) {
        return super.getIcon(size);
    }

    @Override
    public void addGui(PreferenceTabbedPane gui) {
        final JTabbedPane tabbedPane = getTabPane();
        tabbedPane.add(tr("Valhalla"), buildValhallaPane());
        super.addGui(gui);
    }

    private JPanel buildValhallaPane() {
        final JPanel jPanel = new JPanel();
        final JosmComboBoxModel<TransportationType> model = new JosmComboBoxModel<>();
        final JosmComboBox<TransportationType> valhalla = new JosmComboBox<>(model);
        model.addAllElements(EnumSet.allOf(TransportationType.class));
        valhalla.setSelectedItem(this.valhallaConfig.transportationType());
        jPanel.add(new JLabel(tr("Transportation type")));
        jPanel.add(valhalla);
        valhalla.addItemListener(e -> {
            if (e.getItem()instanceof TransportationType type
                    && !type.equals(this.valhallaConfig.transportationType())) {
                this.valhallaConfig = new ValhallaConfig(type, this.valhallaConfig.systemOfMeasurement(),
                        this.valhallaConfig.alternates());
            }
        });
        return jPanel;
    }
}
