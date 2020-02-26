package widzardlib.interfaces;

import widzardlib.models.enums.CallBackType;

public interface AsyncCallback {
    void onCallback(CallBackType callBackType);
}
