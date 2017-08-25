package ir.elmirayafteh.spinalcordinjury.sci;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class SCIApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/B Homa.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
