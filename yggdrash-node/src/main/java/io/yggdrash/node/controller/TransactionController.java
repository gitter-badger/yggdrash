/*
 * Copyright 2018 Akashic Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.yggdrash.node.controller;

import io.yggdrash.core.NodeManager;
import io.yggdrash.core.Transaction;
import io.yggdrash.node.MessageSender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.SignatureException;

@RestController
@RequestMapping("txs")
public class TransactionController {

    private final MessageSender messageSender;

    private final NodeManager nodeManager;

    public TransactionController(NodeManager nodeManager, MessageSender messageSender) {
        this.nodeManager = nodeManager;
        this.messageSender = messageSender;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody TransactionDto request) throws SignatureException {
        try {
            Transaction tx = TransactionDto.of(request);
            Transaction addedTx = nodeManager.addTransaction(tx);
            return ResponseEntity.ok(TransactionDto.createBy(addedTx));
        } catch (IOException e) {
            e.printStackTrace();
            // TODO 에러정의를 다시 해 볼수 있도록 합니다.
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable String id) throws IOException, SignatureException {
        Transaction tx = nodeManager.getTxByHash(id);

        if (tx == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(TransactionDto.createBy(tx));
    }
}
