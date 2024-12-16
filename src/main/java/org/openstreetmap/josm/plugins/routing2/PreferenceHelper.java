// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.plugins.routing2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;
import java.util.Base64;
import java.util.Objects;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.openstreetmap.josm.spi.preferences.Config;
import org.openstreetmap.josm.tools.Logging;

/**
 * A class for reading/writing preferences
 */
public final class PreferenceHelper {
    private PreferenceHelper() {
        // Hide the constructor
    }

    /**
     * Read a record from preferences
     * @param preference The preference to read the record from
     * @param def The default value
     * @return The record from preferences, or the default
     * @param <R> The record type
     */
    @Nonnull
    public static <R extends Record> R readRecord(@Nonnull String preference, @Nonnull R def) {
        Objects.requireNonNull(def, "default value can not be null");
        Objects.requireNonNull(preference, "preference can not be null");
        final String pref = Config.getPref().get(preference);
        if (pref != null && !pref.isEmpty()) {
            try (ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(pref));
                    ObjectInputStream ois = new ObjectInputStream(bais)) {
                Object read = ois.readObject();
                if (def.getClass().isInstance(read)) {
                    return (R) def.getClass().cast(read);
                }
            } catch (IOException e) {
                // Should never happen with ByteArrayInputStream
                throw new UncheckedIOException(e);
            } catch (ClassNotFoundException e) {
                Logging.error(e);
                Config.getPref().put(preference, null); // Reset the preference, just in case.
            }
        }
        return def;
    }

    /**
     * Write a record to preferences
     * @param preference The preference to write to
     * @param rec The record to write
     * @param <R> The record type
     */
    public static <R extends Record> void writeRecord(@Nonnull String preference, @Nullable R rec) {
        Objects.requireNonNull(preference, "preference can not be null");
        if (rec == null) {
            Config.getPref().put(preference, null);
        } else {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(rec);
                oos.flush();
                Config.getPref().put(preference, Base64.getEncoder().encodeToString(baos.toByteArray()));
            } catch (IOException e) {
                // This shouldn't happen with a ByteArrayOutputStream
                throw new UncheckedIOException(e);
            }
        }
    }
}
