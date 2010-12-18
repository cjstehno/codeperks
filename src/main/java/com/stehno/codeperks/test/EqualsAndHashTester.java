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
package com.stehno.codeperks.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Simple utility that adds a full test of equals(Object) and hashCode() methods according
 * to the stated contract.
 *
 * @author cjstehno
 */
public final class EqualsAndHashTester {

	/**
	 * Disallow instantiation
	 */
	private EqualsAndHashTester(){super();}

	/**
	 * Creates the tester. The "same" objects should all be equivalent, but not the same object. The "different"
	 * object should not be equivalent to the other three.
	 *
	 * @param sameA
	 * @param sameB
	 * @param sameC
	 * @param differentA
	 */
	public static void assertValidEqualsAndHash(final Object sameA, final Object sameB, final Object sameC, final Object differentA){
		assertTrue("Not reflexive!",sameA.equals(sameA));
		assertTrue("Not reflexive (hash)!",sameA.hashCode() == sameA.hashCode());

		assertTrue("Not symmetric!",sameA.equals(sameB));
		assertTrue("Not symmetric!",sameB.equals(sameA));
		assertTrue("Not reflexive (hash)!",sameA.hashCode() == sameB.hashCode());

		assertFalse("Not symmetric!",sameA.equals(differentA));
		assertFalse("Not symmetric!",differentA.equals(sameA));
		assertTrue("Non-equivalence (hash)!",sameA.hashCode() != differentA.hashCode());

		assertTrue("Not transitive!",sameA.equals(sameB));
		assertTrue("Not transitive!",sameB.equals(sameC));
		assertTrue("Not transitive!",sameC.equals(sameA));

		assertTrue("Not consistant!",sameA.equals(sameB));
		assertTrue("Not consistant!",sameA.equals(sameB));

		assertFalse("Not consistant!",sameA.equals(differentA));
		assertFalse("Not consistant!",sameA.equals(differentA));

		assertTrue("Not consistant (hash)!",sameA.hashCode() == sameB.hashCode());
		assertTrue("Not consistant (hash)!",sameA.hashCode() == sameB.hashCode());

		assertFalse("Improper null handling",sameA.equals(null));
	}
}
