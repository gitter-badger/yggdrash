/*
 * Copyright 2018 Akashic Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.yggdrash.core.store;

import io.yggdrash.TestUtils;
import io.yggdrash.core.net.Peer;
import io.yggdrash.core.store.datasource.LevelDbDataSource;
import io.yggdrash.util.FileUtil;
import org.junit.AfterClass;
import org.junit.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class PeerStoreTest {
    private PeerStore peerStore;

    @AfterClass
    public static void destroy() {
        FileUtil.recursiveDelete(Paths.get(TestUtils.YGG_HOME));
    }

    @Test
    public void shouldBeGotPeer() {
        peerStore = new PeerStore(new LevelDbDataSource(getPath(), "peers"));

        Peer peer = Peer.valueOf("ynode://75bff16c@127.0.0.1:32918");
        peerStore.put(peer.getPeerId(), peer);

        assertThat(peerStore.contains(peer.getPeerId())).isTrue();

        Peer foundPeer = peerStore.get(peer.getPeerId());
        assertThat(foundPeer).isEqualTo(peer);
    }

    private String getPath() {
        return Paths.get(TestUtils.YGG_HOME, "store").toString();
    }
}