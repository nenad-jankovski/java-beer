package com.javabeer.generator;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;
import com.javabeer.usecase.port.IdGenerator;

public class UuidGenerator implements IdGenerator {

	@Override
	public String generate() {
		return generator().generate().toString();
	}

	private static NoArgGenerator generator() {
		return Generators.timeBasedGenerator(EthernetAddress.fromInterface());
	}
}
