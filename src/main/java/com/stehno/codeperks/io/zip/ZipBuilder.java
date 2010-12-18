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
package com.stehno.codeperks.io.zip;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

/**
 * Uses the builder pattern to build zip files.<br/><br/>
 *
 * <b>Usage</b><br/>
 *
 * <pre>
 * ZipBuilder builder = new ZipBuilder(new BufferedOutputStream(new FileOutputStream("/testfile.zip")))
 * 		.useCompression()
 * 		.setCompressionLevel(
 * </pre>
 *
 * @author Christopher J. Stehno (chris@stehno.com)
 */
public class ZipBuilder {

	private ZipOutputStream zos;

	/**
	 * Creates a zip builder with the given output stream. The stream will be closed when the <code>zip()</code>
	 * method is called.
	 *
	 * @param os the output stream the zip file is to be written into
	 */
	public ZipBuilder(final OutputStream os){
		super();
		this.zos = new ZipOutputStream(os);
	}

	/**
	 * Used to specify the the zip file should be compressed. By default no compression is performed.
	 *
	 * @return a reference to the zip builder
	 */
	public ZipBuilder useCompression(){
		zos.setMethod(ZipOutputStream.DEFLATED);
		return(this);
	}

	/**
	 * Used to specify the level of compression to be used.
	 *
	 * @param compression the level of compression
	 * @return a reference to the builder
	 */
	public ZipBuilder setCompressionLevel(final int compression){
		zos.setLevel(compression);
		return(this);
	}

	/**
	 * Used to specify a zip file level comment. This comment does not seem to be visible to the
	 * Java API itself; however, other applications such as WinZip do recognize it.
	 *
	 * @param comment the file level comment
	 * @return a reference to the builder
	 */
	public ZipBuilder setComment(final String comment){
		zos.setComment(comment);
		return(this);
	}

	/**
	 * Used to add an entry to the zip file.
	 *
	 * @param entry the zip entry to be added
	 * @param bytes the data bytes of the entry data
	 * @return a reference to the zip builder
	 * @throws IOException if there is a problem writing the entry data
	 */
	public ZipBuilder addEntry(final ZipEntry entry, final byte[] bytes) throws IOException {
		zos.putNextEntry(entry);
		zos.write(bytes);
		zos.closeEntry();
		return(this);
	}

	/**
	 * 	Used to add the entry to the zip file. The data from the input stream will be read and
	 * the stream will be closed by this method.
	 *
	 * @param entry the zip entry
	 * @param in the input stream containing the data for the entry
	 * @return a reference to the builder
	 * @throws IOException if there is a problem writing the entry data
	 */
	public ZipBuilder addEntry(final ZipEntry entry, final InputStream in) throws IOException {
		zos.putNextEntry(entry);

		try {IOUtils.copy(in,zos);}
		catch(final IOException ioe){throw ioe;}
		finally {IOUtils.closeQuietly(in);}

		zos.closeEntry();
		return(this);
	}

	/**
	 * Used to add an entry with the given parameters.
	 *
	 * @param name the entry name
	 * @param comment the entry comment
	 * @param bytes the entry data
	 * @return a reference to the builder
	 * @throws IOException if there is a problem writing the entry data
	 */
	public ZipBuilder addEntry(final String name, final String comment, final byte[] bytes) throws IOException {
		final ZipEntry entry = new ZipEntry(name);
		if(isNotBlank(comment)){
			entry.setComment(comment);
		}
		return(addEntry(entry,bytes));
	}

	/**
	 * Used to add an entry with the given data.
	 *
	 * @param name the entry name
	 * @param bytes the entry data
	 * @return a reference to the builder
	 * @throws IOException if there is a problem writing the entry data
	 */
	public ZipBuilder addEntry(final String name, final byte[] bytes) throws IOException {
		return(addEntry(name,null,bytes));
	}

	/**
	 * Used to add an entry with the given parameters. The input stream will be read and closed in this
	 * method.
	 *
	 * @param name the entry name
	 * @param comment the entry comment
	 * @param in the stream containing the entry data
	 * @return a reference to the builder
	 * @throws IOException if there is a problem writing the entry data
	 */
	public ZipBuilder addEntry(final String name, final String comment, final InputStream in) throws IOException {
		final ZipEntry entry = new ZipEntry(name);
		if(isNotBlank(comment)){
			entry.setComment(comment);
		}
		return(addEntry(entry,in));
	}

	/**
	 * Used to add an entry to the zip file. The input stream will be read and closed by this method.
	 *
	 * @param name the entry name
	 * @param in the input stream containing the entry data
	 * @return a reference to the builder
	 * @throws IOException if there is a problem writing the entry data
	 */
	public ZipBuilder addEntry(final String name, final InputStream in) throws IOException {
		return(addEntry(name,null,in));
	}

	/**
	 * 	Used to finish writing the zip file and close out the builder.
	 *
	 * @throws IOException if there is a problem writing the zip file
	 */
	public void zip() throws IOException {
		try {zos.finish();}
		catch(final IOException ioe){throw ioe;}
		finally {IOUtils.closeQuietly(zos);}
	}

	// Dont really want to pull in another dependency just for this
	private boolean isNotBlank(final String s){
		return s != null && s.trim().length() != 0;
	}
}
