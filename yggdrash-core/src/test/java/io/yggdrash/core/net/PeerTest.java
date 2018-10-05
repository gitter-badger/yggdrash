package io.yggdrash.core.net;

import io.yggdrash.core.exception.NotValidateException;
import org.junit.Test;
import org.spongycastle.util.encoders.Hex;

import static org.assertj.core.api.Assertions.assertThat;

public class PeerTest {

    @Test
    public void createPeerTest() {
        Peer peer = Peer.valueOf("ynode://75bff16c22e6b38c71fd2005657827acce3dfd4a1db1cc417303e42"
                + "9d7da9625525ba3f1b7794e104397467f8c5a11c8e86af4ffcc0aefcdf7024013cdc0508d"
                + "@yggdrash-node1:32918");
        assertThat(peer.getHost()).isEqualTo("yggdrash-node1");
        assertThat(peer.getPort()).isEqualTo(32918);
    }

    @Test
    public void createPeerWithNodeIdTest() {
        Peer peer = Peer.valueOf("75bff16c", "yggdrash-node1", 32918);
        assertThat(peer.getYnodeUri()).isEqualTo("ynode://75bff16c@yggdrash-node1:32918");
        assertThat(Hex.decode("75bff16c")).isEqualTo(peer.getId());
    }

    @Test(expected = NotValidateException.class)
    public void unkownSchemaTest() {
        Peer.valueOf("http://75bff16c@yggdrash-node1:32918");
    }
}