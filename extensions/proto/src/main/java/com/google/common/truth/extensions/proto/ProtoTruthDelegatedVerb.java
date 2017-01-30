/*
 * Copyright (c) 2016 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.truth.extensions.proto;

import com.google.common.truth.AbstractDelegatedVerb;
import com.google.common.truth.DelegatedVerbFactory;
import com.google.common.truth.FailureStrategy;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;
import java.util.Map;
import javax.annotation.Nullable;

/**
 * Custom {@link AbstractDelegatedVerb} which aggregates all Proto-related {@link
 * com.google.common.truth.Subject} classes into a single place.
 *
 * <p>To obtain an instance, call {@code assertAbout(ProtoTruth.protos())}.
 */
public final class ProtoTruthDelegatedVerb extends AbstractDelegatedVerb<ProtoTruthDelegatedVerb> {

  /** Factory for ProtoTruthDelegatedVerb. */
  private static class Factory implements DelegatedVerbFactory<ProtoTruthDelegatedVerb> {
    private static final Factory INSTANCE = new Factory();

    @Override
    public ProtoTruthDelegatedVerb createVerb(FailureStrategy failureStrategy) {
      return new ProtoTruthDelegatedVerb(failureStrategy, this);
    }
  }

  static DelegatedVerbFactory<ProtoTruthDelegatedVerb> factory() {
    return Factory.INSTANCE;
  }

  private ProtoTruthDelegatedVerb(
      FailureStrategy failureStrategy, DelegatedVerbFactory<ProtoTruthDelegatedVerb> factory) {
    super(failureStrategy, factory);
  }

  public LiteProtoSubject<?, MessageLite> that(@Nullable MessageLite messageLite) {
    return new LiteProtoSubject.MessageLiteSubject(failureStrategy, messageLite);
  }

  public ProtoSubject<?, Message> that(@Nullable Message message) {
    return new ProtoSubject.MessageSubject(failureStrategy, message);
  }

  public <M extends Message> IterableOfProtosSubject<?, M, Iterable<M>> that(
      @Nullable Iterable<M> messages) {
    return new IterableOfProtosSubject.IterableOfMessagesSubject<M>(failureStrategy, messages);
  }

  public <K, M extends Message> MapWithProtoValuesSubject<?, K, M, Map<K, M>> that(
      @Nullable Map<K, M> map) {
    return new MapWithProtoValuesSubject.MapWithMessageValuesSubject<K, M>(failureStrategy, map);
  }
}
