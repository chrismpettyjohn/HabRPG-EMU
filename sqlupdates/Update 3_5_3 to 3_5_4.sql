--New bot walking settings
INSERT INTO `emulator_settings` (`key`, `value`) VALUES ('hotel.bot.limit.walking.distance', '1');
INSERT INTO `emulator_settings` (`key`, `value`) VALUES ('hotel.bot.limit.walking.distance.radius', '5');

--New permission
ALTER TABLE `permissions` ADD COLUMN `acc_unignorable` ENUM('0','1') NOT NULL DEFAULT '0' AFTER `acc_infinite_friends`;
ALTER TABLE `permissions` ADD COLUMN `cmd_update_chat_bubbles` ENUM('0','1') NOT NULL DEFAULT '0';

--New table for custom chat bubbles
CREATE TABLE chat_bubbles (
    type INT(11) PRIMARY KEY AUTO_INCREMENT COMMENT "Only 46 and higher will work",
    name VARCHAR(255) NOT NULL DEFAULT '',
    permission VARCHAR(255) NOT NULL DEFAULT '',
    overridable BOOLEAN NOT NULL DEFAULT TRUE,
    triggers_talking_furniture BOOLEAN NOT NULL DEFAULT FALSE
);

--New texts for update chat bubbles command
INSERT INTO `emulator_texts` (`key`, `value`) VALUES ('commands.keys.cmd_update_chat_bubbles', 'update_chat_bubbles');
INSERT INTO `emulator_texts` (`key`, `value`) VALUES ('commands.success.cmd_update_chat_bubbles', 'Successfully updated chat bubbles');
INSERT INTO `emulator_texts` (`key`, `value`) VALUES ('commands.description.cmd_update_chat_bubbles', ':update_chat_bubbles');