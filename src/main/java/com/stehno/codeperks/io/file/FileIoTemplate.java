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

import static com.stehno.codeperks.io.IoTemplate.input;
import static com.stehno.codeperks.io.IoTemplate.output;
import static com.stehno.codeperks.io.IoTemplate.read;
import static com.stehno.codeperks.io.IoTemplate.write;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import com.stehno.codeperks.io.InputStreamCallback;
import com.stehno.codeperks.io.OutputStreamCallback;
import com.stehno.codeperks.io.ReaderCallback;
import com.stehno.codeperks.io.WriterCallback;

public class FileIoTemplate {

	// TODO: thinking that I may not really need to factories and callbacks to be full-blown classes, maybe just anon inline classes

	private FileIoTemplate(){}

	public static final void writeFile(final File file, final WriterCallback<BufferedWriter> wc) throws IOException {
		write(new FileWriterFactory(file), wc);
	}

	public static final void readFile(final File file, final ReaderCallback<BufferedReader> rc) throws IOException {
		read(new FileReaderFactory(file),rc);
	}

	public static final void outputFile(final File file, final OutputStreamCallback<BufferedOutputStream> osc) throws IOException {
		output(new FileOutputStreamFactory(file),osc);
	}

	public static final void inputFile(final File file, final InputStreamCallback<BufferedInputStream> isc) throws IOException {
		input(new FileInputStreamFactory(file), isc);
	}
}
