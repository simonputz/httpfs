/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.lib.wsrs;


import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputStreamEntity implements StreamingOutput {
  private InputStream is;
  private long offset;
  private long len;
  private static Logger LOG = LoggerFactory.getLogger(InputStreamEntity.class);

  public InputStreamEntity(InputStream is, long offset, long len) {
    this.is = is;
    this.offset = offset;
    this.len = len;
  }

  public InputStreamEntity(InputStream is) {
    this(is, 0, -1);
  }

  @Override
  public void write(OutputStream os) throws IOException {
    try {
      // if len not equal -1 we copy chunk from offset to len
      // if len equal -1 we copy chunk from offset to end file
      IOUtils.copyLarge(is, os, offset, len);
    }
    finally {
      is.close();
      os.close();
    }
  }
}
