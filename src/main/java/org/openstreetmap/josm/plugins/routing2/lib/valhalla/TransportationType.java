// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.plugins.routing2.lib.valhalla;

/**
 * The transportation type
 */
public enum TransportationType {
    /** Default */
    AUTO,
    /** Bicycle */
    BICYCLE,
    /** Bus */
    BUS,
    /** Bike share */
    BIKE_SHARE,
    /** Truck */
    TRUCK,
    /** Taxi */
    TAXI,
    /** Scooter */
    MOTOR_SCOOTER,
    /** Motorcycle */
    MOTORCYCLE,
    /** Multimodel (e.g. ped + public transit) */
    MULTIMODEL,
    /** Pedestrian */
    PEDESTRIAN,
}
