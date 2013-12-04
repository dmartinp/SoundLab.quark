//Jconvolver

//notes
//config file
//path in config file relative to config file

Jconvolver {
	var kernelFolderPath, compensateLatency, configFileStringArr;
	var <partitionSize, <maxKernelSize, <numInChannels, <numOutChannels, channelMatrix;
	var <pid;
	classvar <>jackScOutNameDefault = "SuperCollider:out_"; // for supernova: "supernova:output_"
	classvar <>jackScInNameDefault = "SuperCollider:in_"; // for supernova: "supernova:input_"
	classvar <>jackJconvolverOutNameDefault = "jconvolver:Out-";
	classvar <>jackJconvolverInNameDefault = "jconvolver:In-";
	classvar <>jackSystemOutNameDefault = "system:playback_";
	classvar <>executablePath = "jconvolver";
	classvar <>configFileName = "jconvolver.conf";

	// srcChannels: number for number of channels starting at first, array for anything else, 0-based
	// dstChannels: (use srcCh) or an array, 0-based
	// probably not needed, as configuration file provides auto connect functionality
	*connectScToSystemOut { |srcChannels = 28, dstChannels, srcPort, dstPort|
		srcPort ?? {srcPort = jackScOutNameDefault};
		dstPort ?? {dstPort = jackSystemOutNameDefault};
		this.prConnectJack(srcChannels, dstChannels, srcPort, dstPort);
	}

	*connectScToJconvolver { |srcChannels = 28, dstChannels, srcPort, dstPort|
		srcPort ?? {srcPort = jackScOutNameDefault};
		dstPort ?? {dstPort = jackJconvolverInNameDefault};
		this.prConnectJack(srcChannels, dstChannels, srcPort, dstPort);
	}

	*connectJconvolverToSystemOut { |srcChannels = 28, dstChannels, srcPort, dstPort|
		srcPort ?? {srcPort = jackJconvolverOutNameDefault};
		dstPort ?? {dstPort = jackSystemOutNameDefault};
		this.prConnectJack(srcChannels, dstChannels, srcPort, dstPort);
	}

	*connectJconvolverToSc { |srcChannels = 28, dstChannels, srcPort, dstPort|
		srcPort ?? {srcPort = jackJconvolverOutNameDefault};
		dstPort ?? {dstPort = jackScInNameDefault};
		this.prConnectJack(srcChannels, dstChannels, srcPort, dstPort);
	}

	*disconnectScFromSystemOut { |srcChannels = 28, dstChannels, srcPort, dstPort|
		srcPort ?? {srcPort = jackScOutNameDefault};
		dstPort ?? {dstPort = jackSystemOutNameDefault};
		this.prDisconnectJack(srcChannels, dstChannels, srcPort, dstPort);
	}

	*prConnectJack {|srcCh, dstCh, srcPort, dstPort|
		if(srcCh.isKindOf(SimpleNumber), {
			srcCh = (0..(srcCh-1));
		});
		if(dstCh.isNil, {
			dstCh = srcCh;
		});
		srcCh.do({|thisChan|
			SCJConnection.connect([thisChan + 1], [dstCh[thisChan] + 1], srcPort, dstPort);
		});
	}

	*prDisconnectJack {|srcCh, dstCh, srcPort, dstPort|
		if(srcCh.isKindOf(SimpleNumber), {
			srcCh = (0..(srcCh-1));
		});
		if(dstCh.isNil, {
			dstCh = srcCh;
		});
		srcCh.do({|thisChan|
			SCJConnection.disconnect([thisChan + 1], [dstCh[thisChan] + 1], srcPort, dstPort);
		});
	}

	*killAll{
		"killall jconvolver".unixCmd;
	}

	// add number of kernels limit
	// everything 0-based...
	// add automatic max size
	// num ins and outs equals number of found kernels, also uses 1:1 matrix, assumes single channel kernels,
	// autoConnect.. if simple number, will connect starting on that channel (with respective number of ins/outs),
	// 	if array, will map to specified input...
	// maxKernelSize must be larger than numFrames of the kernel
	*createSimpleConfigFileFromFolder {|kernelFolderPath, partitionSize = 1024, maxKernelSize = 100000, matchFileName = "*.wav", configFilePath, autoConnectToScChannels = 28, autoConnectToSoundcardChannels = 0|
		var soundFilesArray, useRelativePaths, file;
		var numInChannels, numOutChannels;
		if(configFilePath.isNil, { //not providing configFilePath creates the file in the same folder as kernels and assumes relative paths to kernels
			useRelativePaths = true;
			configFilePath = kernelFolderPath.withTrailingSlash ++ configFileName;
			}, {
				useRelativePaths = false;
		});
		soundFilesArray = SoundFile.collect(kernelFolderPath.withTrailingSlash ++ matchFileName);
		numInChannels = numOutChannels = soundFilesArray.size;
		//write the file
		file = File(configFilePath,"w");
		file.write("#generated by SuperCollider Jconvolver class \n\n");
		//init convolver
		file.write("/convolver/new" + numInChannels.asString + numOutChannels.asString + partitionSize.asString + maxKernelSize.asString ++ "\n\n");
		if(autoConnectToScChannels.notNil, {
			if(autoConnectToScChannels.isKindOf(SimpleNumber), {
				autoConnectToScChannels = (autoConnectToScChannels..(autoConnectToScChannels + soundFilesArray.size - 1));
			});
			if(autoConnectToScChannels.size == soundFilesArray.size, {
				//connections - ins
				numInChannels.do({|inc|
					file.write("/input/name" + (inc + 1).asString + "In-" ++ (inc + 1).asString + jackScOutNameDefault ++ (autoConnectToScChannels[inc] + 1).asString ++ "\n");
				});
				file.write("\n");
				}, {
					"scChannels array different size from number of convolution channels, not connecting".warn;
			});
		});
		if(autoConnectToSoundcardChannels.notNil, {
			if(autoConnectToSoundcardChannels.isKindOf(SimpleNumber), {
				autoConnectToSoundcardChannels = (autoConnectToSoundcardChannels..(autoConnectToSoundcardChannels + soundFilesArray.size - 1));
			});
			if(autoConnectToSoundcardChannels.size == soundFilesArray.size, {
				//connections - outs
				numOutChannels.do({|inc|
					file.write("/output/name" + (inc + 1).asString + "Out-" ++ (inc + 1).asString + jackSystemOutNameDefault ++ (autoConnectToSoundcardChannels[inc] + 1).asString ++ "\n");
				});
				file.write("\n");
				}, {
					"soundcardChannels array different size from number of convolution channels, not connecting".warn;
			});
		});
		//kernels
		soundFilesArray.do({|thisFile, inc|
			var thisPath;
			if(useRelativePaths, {
				thisPath = thisFile.path.asRelativePath(kernelFolderPath.withoutTrailingSlash);
				}, {
					thisPath = thisFile.path;
			});
			//in out  gain  delay  offset  length  chan      file
			file.write("/impulse/read" + (inc + 1).asString + (inc + 1).asString + 1.asString + 0.asString + 0.asString + 0.asString + 1.asString + thisPath ++ "\n");
		});
		file.close;
		"Jconvolver config file created successfully!".postln;
	}

	*newFromFolder {|kernelFolderPath, compensateLatency /*frames*/|
		if(File.exists(kernelFolderPath.withTrailingSlash ++ configFileName).not, {
			"no configuration file in the folder, creating one with default config".postln;
			this.createSimpleConfigFileFromFolder(kernelFolderPath: kernelFolderPath);
		});
		^super.newCopyArgs(kernelFolderPath, compensateLatency).init;
	}

	init{
		var command;
		//get data
		"Jconvolver class: reading configuration file".postln;
		configFileStringArr = FileReader.read(kernelFolderPath.withTrailingSlash ++ configFileName, true);
		configFileStringArr.do({|thisArr, inc|
			// thisArr.postln;
			if(thisArr[0] == "/convolver/new", {
				numInChannels = thisArr[1].asInteger;
				numOutChannels = thisArr[2].asInteger;
				partitionSize = thisArr[3].asInteger;
				maxKernelSize = thisArr[4].asInteger;
			});
		});
		//start convolver
		"Starting jconvolver...".postln;
		// command = executablePath;
		command = "exec" + executablePath; //this fixes PID issues on my linux system; should work on OSX without problems
		if(compensateLatency.notNil, {
			command = command + "-L" + compensateLatency.asString;
		});
		command = command + kernelFolderPath.withTrailingSlash ++ configFileName;
		command.postln;
		pid = command.unixCmd;
		("jconvolver started with pid:"+pid).postln;
	}

	free{
		("kill -15 " ++ pid.asString).unixCmd;
	}
	isRunning {
		^pid.pidRunning;
	}
}