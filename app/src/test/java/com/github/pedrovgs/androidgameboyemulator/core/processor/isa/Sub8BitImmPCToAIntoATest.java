/*
 * Copyright (C) 2015 Pedro Vicente Gomez Sanchez.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.github.pedrovgs.androidgameboyemulator.core.processor.isa;

import com.github.pedrovgs.androidgameboyemulator.InstructionTest;
import com.github.pedrovgs.androidgameboyemulator.core.processor.Register;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class Sub8BitImmPCToAIntoATest extends InstructionTest {

  @Test public void shouldUseOneAsLastInstructionExecutionTime() {
    Instruction instruction = new Sub8BitImmPCToAIntoA(z80, mmu);

    instruction.execute();

    assertEquals(2, z80.getLastInstructionExecutionTime());
  }

  @Test public void shouldSubTheContentOfTheMemoryAddressPointedByThePCAndStoreTheResultIntoA() {
    z80.setProgramCounter(ANY_16BIT_REGISTER_VALUE);
    when(mmu.readByte(ANY_16BIT_REGISTER_VALUE)).thenReturn(ANY_MEMORY_BYTE_VALUE);
    z80.set8BitRegisterValue(Register.A, (byte) (ANY_MEMORY_BYTE_VALUE + 1));
    Instruction instruction = new Sub8BitImmPCToAIntoA(z80, mmu);

    instruction.execute();

    assertEquals(1, z80.get8BitRegisterValue(Register.A));
  }

  @Test public void shouldEnableFlagZIfResultIsZero() {
    z80.setProgramCounter(ANY_16BIT_REGISTER_VALUE);
    when(mmu.readByte(ANY_16BIT_REGISTER_VALUE)).thenReturn(ANY_MEMORY_BYTE_VALUE);
    z80.set8BitRegisterValue(Register.A, ANY_MEMORY_BYTE_VALUE);
    Instruction instruction = new Sub8BitImmPCToAIntoA(z80, mmu);

    instruction.execute();

    assertTrue(z80.isFlagZEnabled());
  }

  @Test public void shouldDisableFlagZIfResultIsNotZero() {
    z80.setProgramCounter(ANY_16BIT_REGISTER_VALUE);
    when(mmu.readByte(ANY_16BIT_REGISTER_VALUE)).thenReturn(ANY_MEMORY_BYTE_VALUE);
    z80.set8BitRegisterValue(Register.A, (byte) (ANY_MEMORY_BYTE_VALUE + 1));
    Instruction instruction = new Sub8BitImmPCToAIntoA(z80, mmu);

    instruction.execute();

    assertFalse(z80.isFlagZEnabled());
  }

  @Test public void shouldEnableFlagN() {
    Instruction instruction = new Sub8BitImmPCToAIntoA(z80, mmu);

    instruction.execute();

    assertTrue(z80.isFlagNEnabled());
  }
}