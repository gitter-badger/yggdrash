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

package io.yggdrash.core;

import io.yggdrash.TestUtils;
import io.yggdrash.core.exception.FailedOperationException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BlockChainBuilderTest {

    private BlockChainBuilder blockChainBuilder;

    @Before
    public void setUp() {
        blockChainBuilder = new BlockChainBuilder();
    }

    @Test(expected = FailedOperationException.class)
    public void buildStemBlockChain() {
        Branch branch = Branch.of(BranchId.STEM, Branch.STEM, TestUtils.OWNER);
        blockChainBuilder.buildBlockChain(TestUtils.wallet(), branch, false);
    }

    @Test
    public void buildYeedBlockChain() {
        Branch branch = Branch.of(BranchId.YEED, Branch.YEED, TestUtils.OWNER);
        BlockChain blockChain = blockChainBuilder.buildBlockChain(TestUtils.wallet(), branch, false);
        assertEquals(blockChain.getBranchId(), BranchId.yeed());

        BlockHusk genesis = TestUtils.createGenesisBlockHusk();
        blockChain = blockChainBuilder.buildBlockChain(genesis, Branch.YEED, false);
        assertEquals(blockChain.getGenesisBlock().getHash(), genesis.getHash());
    }
}