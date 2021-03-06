package io.yggdrash.contract;

import com.google.gson.JsonObject;
import io.yggdrash.core.TransactionHusk;
import io.yggdrash.core.store.StateStore;
import io.yggdrash.core.store.TransactionReceiptStore;

public interface Contract<T> {
    void init(StateStore<T> store, TransactionReceiptStore txReceiptStore);

    boolean invoke(TransactionHusk tx);

    JsonObject query(JsonObject query) throws Exception;
}
