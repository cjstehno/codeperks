package com.stehno.codeperks.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class IoTemplateTest {

	private static final String DATA = "some interesting test data";

	@Test
	public void write() throws IOException {
		final StringWriter writer = new StringWriter();

		final WriterFactory<StringWriter> wf = new WriterFactory<StringWriter>(){
			@Override
			public StringWriter writer() throws IOException {
				return writer;
			}
		};

		IoTemplate.write(wf, new MockWriterCallback());

		assertEquals(DATA,writer.toString());
	}

	@Test
	public void write_writer() throws IOException {
		final StringWriter writer = new StringWriter();

		IoTemplate.write(writer, new MockWriterCallback());

		assertEquals(DATA,writer.toString());
	}

	@Test(expected=IOException.class)
	public void write_with_factory_exception() throws IOException {
		final StringWriter writer = new StringWriter();

		final WriterFactory<StringWriter> wf = new WriterFactory<StringWriter>(){
			@Override
			public StringWriter writer() throws IOException {
				throw new IOException();
			}
		};

		IoTemplate.write(wf, new MockWriterCallback());

		assertEquals(DATA,writer.toString());
	}

	static class MockWriterCallback implements WriterCallback<StringWriter> {
		@Override
		public void write(final StringWriter w) throws IOException {
			w.write(DATA);
		}
	}

	@Test
	public void output() throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();

		final OutputStreamFactory<PrintStream> osf = new OutputStreamFactory<PrintStream>() {
			@Override
			public PrintStream outputStream() throws IOException {
				return new PrintStream(baos);
			}
		};

		IoTemplate.output(osf, new MockOutputStreamCallback());

		assertEquals(DATA,baos.toString());
	}

	@Test
	public void output_with_stream() throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintStream ps = new PrintStream(baos);

		IoTemplate.output(ps, new MockOutputStreamCallback());

		assertEquals(DATA,baos.toString());
	}

	@Test(expected=IOException.class)
	public void output_with_factory_exception() throws IOException {
		final OutputStreamFactory<PrintStream> osf = new OutputStreamFactory<PrintStream>() {
			@Override
			public PrintStream outputStream() throws IOException {
				throw new IOException();
			}
		};

		IoTemplate.output(osf, new MockOutputStreamCallback());
	}

	static class MockOutputStreamCallback implements OutputStreamCallback<PrintStream> {
		@Override
		public void output(final PrintStream out) throws IOException {
			out.print(DATA);
		}
	}

	@Test
	public void read() throws IOException {
		final ReaderFactory<StringReader> rf = new ReaderFactory<StringReader>() {
			@Override
			public StringReader reader() throws IOException {
				return new StringReader(DATA);
			}
		};

		final MockReaderCallback rc = new MockReaderCallback();

		IoTemplate.read(rf, rc);

		assertEquals(DATA,rc.toString());
	}

	@Test
	public void read_with_reader() throws IOException {
		final MockReaderCallback rc = new MockReaderCallback();

		IoTemplate.read(new StringReader(DATA), rc);

		assertEquals(DATA,rc.toString());
	}

	@Test(expected=IOException.class)
	public void read_with_factory_exception() throws IOException {
		final ReaderFactory<StringReader> rf = new ReaderFactory<StringReader>() {
			@Override
			public StringReader reader() throws IOException {
				throw new IOException();
			}
		};

		IoTemplate.read(rf, new MockReaderCallback());
	}

	static class MockReaderCallback implements ReaderCallback<StringReader> {
		private String value;

		@Override
		public void read(final StringReader reader) throws IOException {
			value = IOUtils.toString(reader);
		}

		@Override
		public String toString(){return value;}
	}

	@Test
	public void input() throws IOException {
		final InputStreamFactory<InputStream> isf = new InputStreamFactory<InputStream>() {
			@Override
			public InputStream inputStream() throws IOException {
				return new ByteArrayInputStream(DATA.getBytes());
			}
		};

		final MockInputStreamCallback isc = new MockInputStreamCallback();

		IoTemplate.input(isf, isc);

		assertEquals(DATA,isc.toString());
	}

	@Test
	public void input_with_stream() throws IOException {
		final MockInputStreamCallback isc = new MockInputStreamCallback();

		IoTemplate.input(new ByteArrayInputStream(DATA.getBytes()), isc);

		assertEquals(DATA,isc.toString());
	}

	@Test(expected=IOException.class)
	public void input_with_factory_exception() throws IOException {
		final InputStreamFactory<InputStream> isf = new InputStreamFactory<InputStream>() {
			@Override
			public InputStream inputStream() throws IOException {
				throw new IOException();
			}
		};

		IoTemplate.input(isf, new MockInputStreamCallback());
	}

	static class MockInputStreamCallback implements InputStreamCallback<InputStream> {
		private String value;

		@Override
		public void input(final InputStream in) throws IOException {
			value = IOUtils.toString(in);
		}

		@Override
		public String toString(){return value;}
	}
}
