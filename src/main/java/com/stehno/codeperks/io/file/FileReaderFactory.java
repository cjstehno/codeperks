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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.stehno.codeperks.io.ReaderFactory;

public class FileReaderFactory implements ReaderFactory<BufferedReader> {

	private final File file;

	public FileReaderFactory(final File file){
		this.file = file;
	}

	@Override
	public BufferedReader reader() throws IOException {
		return new BufferedReader( new FileReader(file) );
	}
}
