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
package com.stehno.codeperks.io.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.stehno.codeperks.io.WriterFactory;

public class FileWriterFactory implements WriterFactory<BufferedWriter> {

	private File file;

	public FileWriterFactory(final File file){
		this.file = file;
	}

	@Override
	public BufferedWriter writer() throws IOException {
		return new BufferedWriter( new FileWriter(file) );
	}
}
