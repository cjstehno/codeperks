/*
 *	Copyright 2009 Christopher J. Stehno (chris@stehno.com)
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
package com.stehno.codeperks.lang.retry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class RetrierTest {

    private Retrier retrier;
    @Mock Retriable<String> operation;

    @Before
    public void before(){
        this.retrier = new Retrier(3);
    }

    @Test
    public void retries_no_exceptions() throws Exception {
        final String value = "foo";

        when(operation.execute()).thenReturn( value );

        final Object result = retrier.execute( operation );
        assertNotNull(result);
        assertEquals(value,result);
    }

    @Test
    public void reties_all_exceptions() throws Exception {
        when(operation.execute()).thenThrow( new Exception("ow") )
        .thenThrow( new Exception("ow") )
        .thenThrow( new Exception("ow") );

        try {
                retrier.execute( operation );
                fail("Expected exception not thrown!");
        } catch(final Exception e) {
                assertMaxExceededException( e, 3 );
        }
    }

    @Test
    public void reties_five_exceptions() throws Exception {
        retrier.setMaxRetries(5);

        when(operation.execute())
        .thenThrow( new Exception("ow") )
        .thenThrow( new Exception("ow") )
        .thenThrow( new Exception("ow") )
        .thenThrow( new Exception("ow") )
        .thenThrow( new Exception("ow") );

        try {
                retrier.execute( operation );
                fail("Expected exception not thrown!");
        } catch(final Exception e) {
                assertMaxExceededException( e, 5 );
        }
    }

    @Test
    public void reties_two_exceptions() throws Exception {
        final String value = "foo";

        when(operation.execute())
        .thenThrow( new Exception("ow") )
        .thenThrow( new Exception("ow") )
        .thenReturn( value );

        final Object result = retrier.execute( operation );
        assertNotNull(result);
        assertEquals(value,result);
    }

    @SuppressWarnings("unchecked")
    @Test(expected=NullPointerException.class)
    public void reties_throw_exceptions() throws Exception {
        retrier.setCatchAndThrow(new Class[]{ NullPointerException.class });

        when(operation.execute())
        .thenThrow( new Exception("ow") )
        .thenThrow( new Exception("ow") )
        .thenThrow( new NullPointerException());

        retrier.execute( operation );
    }

    @Test
    public void reties_exceptions_after_success() throws Exception {
        final String value = "foo";

        when(operation.execute())
        .thenThrow( new Exception("ow") )
        .thenReturn( value )
        .thenThrow( new Exception("ow"));

        final String result = retrier.execute( operation );
        assertNotNull(result);
        assertEquals(value,result);
    }

    @SuppressWarnings("unchecked")
    @Test(expected=IllegalStateException.class)
    public void reties_throw_some_exceptions() throws Exception {
        retrier.setCatchAndThrow(new Class[]{ IllegalStateException.class });

        when(operation.execute())
        .thenThrow( new Exception() )
        .thenThrow( new IllegalStateException())
        .thenThrow( new Exception());

        retrier.execute( operation );
    }

    @After
    public void after(){
        this.retrier = null;
    }

    private void assertMaxExceededException(final Exception e, final int expectedCount){
        assertTrue( e instanceof MaxRetriesExceededException);
        assertNotNull( e.getMessage() );
        assertEquals( expectedCount, ((MaxRetriesExceededException)e).getMax() );
    }
}
