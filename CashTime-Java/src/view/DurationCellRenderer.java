package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.time.Duration;

public class DurationCellRenderer extends DefaultTableCellRenderer {
    @Override
    protected void setValue(Object value) {
        if (value instanceof Duration) {
            Duration duration = (Duration) value;
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            setText(hours + "h" + minutes + "m");
        } else {
            super.setValue(value);
        }
    }
}

