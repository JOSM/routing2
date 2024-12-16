// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.plugins.routing2.lib.valhalla;

import java.io.Serializable;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.openstreetmap.josm.data.SystemOfMeasurement;

/**
 * A configuration record for valhalla
 * @param transportationType The transportation type to use
 * @param systemOfMeasurement The system of measurement to use, or the default for the download area
 * @param alternates The number of alternate routes to generate
 */
public record ValhallaConfig(@Nonnull TransportationType transportationType, @Nullable SystemOfMeasurement systemOfMeasurement, int alternates) implements Serializable {
    /** The default configuration */
    public static ValhallaConfig DEFAULT = new ValhallaConfig(TransportationType.AUTO, null, 3);
}
