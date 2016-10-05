package com.eveningoutpost.dexdrip.calibrations;

import com.eveningoutpost.dexdrip.Models.Calibration;
import com.eveningoutpost.dexdrip.Models.UserError;

/**
 * Created by jamorham on 04/10/2016.
 * <p>
 * This mirrors the standard xDrip calibration algorithm
 * in the plugin format. Beware it doesn't reimplement it,
 * only pulls the values from the standard database.
 */

public class XDripOriginal extends CalibrationAbstract {

    private static final String TAG = "xDrip Original";

    @Override
    public String getAlgorithmName() {
        return TAG;
    }

    @Override
    public String getAlgorithmDescription() {
        return " classic algorithm";
    }

    @Override
    public CalibrationData getCalibrationData() {

        CalibrationData cd = loadDataFromCache(TAG);
        if (cd == null) {
            UserError.Log.d(TAG, "Regenerating Calibration data cache");
            final Calibration calibration = Calibration.lastValid();
            if (calibration != null) {

                // produce the CalibrationData result
                cd = new CalibrationData(calibration.slope, calibration.intercept);

                saveDataToCache(TAG, cd);
            }
        //} else {
        //    UserError.Log.d(TAG, "Using cached data");
        }
        return cd; // null if invalid
    }

    @Override
    public boolean invalidateCache() {
        return saveDataToCache(TAG, null);
    }

    // xDrip classic algorithm overrides raw interpolated sensor data near
    // to a calibration
    @Override
    public boolean newCloseSensorData() {
        return invalidateCache();
    }
}
