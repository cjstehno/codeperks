/*
 * Copyright 2009 Christopher J. Stehno
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.stehno.codeperks.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Callback use by template methods to provide a managed writable OutputStream.
 *
 * @author Christopher J. Stehno (chris@stehno.com)
 *
 * @param <O> the type of OutputStream
 */
public interface OutputStreamCallback<O extends OutputStream> {

	/**
	 * Provides a writable OutputStream of the given type so that data
	 * can be written out without concern for stream management.
	 *
	 * @param out an OutputStream of the given type
	 * @throws IOException if there is a problem writing to the stream
	 */
	void output(O out) throws IOException;
}
