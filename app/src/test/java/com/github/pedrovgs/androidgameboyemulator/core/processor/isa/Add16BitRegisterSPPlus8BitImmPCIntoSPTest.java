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
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class Add16BitRegisterSPPlus8BitImmPCIntoSPTest extends InstructionTest {

  @Test public void shouldUseFourCyclesAsLastInstructionExecutionTime() {
    Instruction instruction = new Add16BitRegisterSPPlus8BitImmPCIntoSP(z80, mmu);

    instruction.execute();

    assertEquals(4, z80.getLastInstructionExecutionTime());
  }

  @Test public void shouldIncrementProgramCounter() {
    z80.setProgramCounter(ANY_16BIT_REGISTER_VALUE);
    Instruction instruction = new Add16BitRegisterSPPlus8BitImmPCIntoSP(z80, mmu);

    instruction.execute();

    assertEquals(ANY_16BIT_REGISTER_VALUE + 1, z80.getProgramCounter());
  }

  @Test public void shouldSumTheValueOfSPPlusTheMemoryPointedByPCAndStoreTheResultIntoRegisterSP() {
    z80.setStackPointer(ANY_16BIT_REGISTER_VALUE);
    z80.setProgramCounter(ANY_16BIT_REGISTER_VALUE);
    when(mmu.readByte(ANY_16BIT_REGISTER_VALUE)).thenReturn(ANY_MEMORY_BYTE_VALUE);
    Instruction instruction = new Add16BitRegisterSPPlus8BitImmPCIntoSP(z80, mmu);

    instruction.execute();

    int expectedValue = ANY_16BIT_REGISTER_VALUE + ANY_MEMORY_BYTE_VALUE;
    assertEquals(expectedValue, z80.getStackPointer());
  }
}