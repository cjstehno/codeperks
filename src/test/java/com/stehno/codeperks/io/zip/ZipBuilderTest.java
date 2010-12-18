package com.stehno.codeperks.io.zip;
/*
 *	Copyright 2006 Christopher J. Stehno (chris@stehno.com)
 *
 * 	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 * 	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */


import com.stehno.codeperks.io.zip.ZipBuilder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ZipBuilderTest {

	private static final String[] ENTRY_NAME = {"ZipEntryName","AnotherZipEntryName"};
	private static final String[] COMMENT_ENTRYLEVEL = {"This is an entry-level comment","This is another entry-level comment"};
	private static final byte[][] DATA_ARRAY = {"This is some content".getBytes(),"This is some more content".getBytes()};

	private File zipfile;
	private ZipBuilder zipBuilder;

	@Before
	public void before() throws IOException {
		this.zipfile = createTempFile();
		final FileOutputStream zos = new FileOutputStream( zipfile );
		this.zipBuilder = new ZipBuilder(zos).useCompression().setCompressionLevel(Deflater.BEST_COMPRESSION);
	}

	@Test
	public void addEntry_EntryAndBytes() throws Exception {
		zipBuilder.setComment("some comment"); // cannot currently test for file level comments

		for(int i=0; i<ENTRY_NAME.length; i++){
			final ZipEntry entry = new ZipEntry(ENTRY_NAME[i]);
			entry.setComment(COMMENT_ENTRYLEVEL[i]);

			zipBuilder.addEntry(entry,DATA_ARRAY[i]);
		}

		zipBuilder.zip();

		// asserts
		final ZipFile zf = getZipFile();
		assertEquals(2,zf.size());

		for(int i=0; i<ENTRY_NAME.length; i++){
			assertZipEntry(zf,ENTRY_NAME[i], COMMENT_ENTRYLEVEL[i], DATA_ARRAY[i]);
		}
	}

	@Test
	public void addEntry_Name_Comment_Input() throws Exception {
		zipBuilder.addEntry(ENTRY_NAME[0], COMMENT_ENTRYLEVEL[0], new ByteArrayInputStream(DATA_ARRAY[0]));
		zipBuilder.zip();

		final ZipFile zf = getZipFile();
		assertEquals(1,zf.size());

		assertZipEntry( zf, ENTRY_NAME[0], COMMENT_ENTRYLEVEL[0], DATA_ARRAY[0] );
	}

	@Test
	public void addEntry_Name_Comment_Bytes() throws Exception {
		zipBuilder.addEntry(ENTRY_NAME[0], COMMENT_ENTRYLEVEL[0], DATA_ARRAY[0]);
		zipBuilder.zip();

		final ZipFile zf = getZipFile();
		assertEquals(1,zf.size());

		assertZipEntry( zf, ENTRY_NAME[0], COMMENT_ENTRYLEVEL[0], DATA_ARRAY[0] );
	}

	@Test
	public void addEntry_Name_Bytes() throws Exception {
		zipBuilder.addEntry(ENTRY_NAME[0], DATA_ARRAY[0]);
		zipBuilder.zip();

		final ZipFile zf = getZipFile();
		assertEquals(1,zf.size());

		assertZipEntry( zf, ENTRY_NAME[0], null, DATA_ARRAY[0] );
	}

	@Test
	public void addEntry_Name_Stream() throws Exception {
		zipBuilder.addEntry(ENTRY_NAME[0], new ByteArrayInputStream(DATA_ARRAY[0]));
		zipBuilder.zip();

		final ZipFile zf = getZipFile();
		assertEquals(1,zf.size());

		assertZipEntry( zf, ENTRY_NAME[0], null, DATA_ARRAY[0] );
	}

	@Test
	public void addEntry_EntryAndInputstream() throws Exception {
		for(int i=0; i<ENTRY_NAME.length; i++){
			final ZipEntry entry = new ZipEntry(ENTRY_NAME[i]);
			entry.setComment(COMMENT_ENTRYLEVEL[i]);

			zipBuilder.addEntry(entry,new ByteArrayInputStream(DATA_ARRAY[i]));
		}

		zipBuilder.zip();

		// asserts
		final ZipFile zf = getZipFile();
		assertEquals(2,zf.size());

		for(int i=0; i<ENTRY_NAME.length; i++){
			assertZipEntry(zf,ENTRY_NAME[i], COMMENT_ENTRYLEVEL[i], DATA_ARRAY[i]);
		}
	}

	private void assertZipEntry(final ZipFile zf,final String name, final String comment, final byte[] data) throws IOException {
		final ZipEntry ze = zf.getEntry(name);
		assertNotNull("Entry is null!",ze);
		assertEquals("Entry names don't match!",name, ze.getName());
		assertEquals("Comments don't match!",comment, ze.getComment());
		assertTrue( "Data arrays don't match!", Arrays.equals(data, IOUtils.toByteArray(zf.getInputStream(ze))) );
	}

	private File createTempFile() throws IOException {
		final File file = File.createTempFile("zipbuildertest.",null);
		file.deleteOnExit();
		return(file);
	}

	private ZipFile getZipFile() throws Exception {
		return new ZipFile(zipfile);
	}

	@After
	public void after(){
		FileUtils.deleteQuietly(zipfile);
		this.zipBuilder = null;
	}
}
