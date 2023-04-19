package my.edu.utar.periodtrackertowl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;

public class ResourcesWrapper extends Resources {
    private Resources mOriginalResources;
    private Resources mLocalizedResources;

    public ResourcesWrapper(Resources originalResources, Resources localizedResources) {
        super(originalResources.getAssets(), originalResources.getDisplayMetrics(), originalResources.getConfiguration());
        mOriginalResources = originalResources;
        mLocalizedResources = localizedResources;
    }

    @Override
    public CharSequence getText(int id) throws NotFoundException {
        return mLocalizedResources.getText(id);
    }

    @Override
    public CharSequence getQuantityText(int id, int quantity) throws NotFoundException {
        return mLocalizedResources.getQuantityText(id, quantity);
    }

    @Override
    public String getString(int id) throws NotFoundException {
        return mLocalizedResources.getString(id);
    }

    @Override
    public String getString(int id, Object... formatArgs) throws NotFoundException {
        return mLocalizedResources.getString(id, formatArgs);
    }

    @Override
    public String getQuantityString(int id, int quantity, Object... formatArgs) throws NotFoundException {
        return mLocalizedResources.getQuantityString(id, quantity, formatArgs);
    }

    @Override
    public String getQuantityString(int id, int quantity) throws NotFoundException {
        return mLocalizedResources.getQuantityString(id, quantity);
    }
}
