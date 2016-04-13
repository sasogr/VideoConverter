USE videoconverter;

# Stores the general options of FFmpeg, ffprobe and ffmpeg
CREATE TABLE FFMPEG_GENERAL (
	id INT NOT NULL PRIMARY KEY,
	name VARCHAR(1000),
	commandValue VARCHAR(1000)
);

# Stores the options of the available general commands in the FFMPEG_GENERAL table. 
CREATE TABLE FFMPEG_OPTIONS (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	optionTo INT NOT NULL,
	name VARCHAR(1000),
	commandValue VARCHAR(1000),
	acceptInput TINYINT(1),
	FOREIGN KEY (optionTo) REFERENCES FFMPEG_GENERAL(id)
		ON UPDATE CASCADE ON DELETE CASCADE
);

# Insert data in FFMPEG_GENERAL
INSERT INTO FFMPEG_GENERAL (id, name, commandValue) 
VALUES (1,'ffprobe','ffprobe'), (2,'ffmpeg','ffmpeg');

# Insert ffprobe data into FFMPEG_OPTIONS
INSERT INTO FFMPEG_OPTIONS (optionTo, name, commandValue, acceptInput)
VALUES (1, '-v error', '-v error', 0), (1, '-show_entries', '-show_entries', 0),
(1, 'format', 'format', 1), (1, 'streams', 'streams', 0), 
(1, '-select_streams', '-select_streams', 1), (1, 'stream', 'stream', 1), 
(1, '-show_frames', '-show_frames', 0);

# Insert ffmpeg data in FFMPEG_GENERAL
INSERT INTO FFMPEG_OPTIONS (optionTo, name, commandValue, acceptInput)
VALUES (2, '-codecs', '-codecs', 0), (2, '-c:v', '-c:v', 1), (2, '-c:a', '-c:a', 1), 
(2, '-b', '-b', 1), (2, '-vframes', '-vframes', 1), (2, '-r', '-r', 1), 
(2, '-s', '-s', 1), (2, '-aspect', '-aspect', 1), (2, '-maxrate', '-maxrate', 1), 
(2, '-bufsize', '-bufsize', 1), (2, '-minrate', '-minrate', 1);