users(user ids should ideally be filled by identity provider) \#\#\# FIX USER IDS

| id | username | role-id(fk) |
| :---- | :---- | :---- |

workbench-ami

| id | workbench-id | ami-id |
| :---- | :---- | :---- |

role-workbench 

| id | roleid (fk)(indexed) | workbench-id (fk) |
| :---- | :---- | :---- |

role

| id | role-name |
| :---- | :---- |

administrative-limits

limits that we are considering \- (configurable by admin only)

1. per user per workbench per day time limit  
2. simultaneous workbench per user

| id | limit-type | unit | limit-description | default-value |
| :---- | :---- | :---- | :---- | :---- |
|  | MAX\_CONCURRENT | count | simultaneous workbench per user |  |
|  | MAX\_DAILY\_TIME | minutes | per user per workbench per day time limit |  |

workbench-usage (also works as log of start and stop of workbenches) 

- if stopped at is null then usage is current \- started\_at

| id | userid | workbench-id | instance-id | started\_at | stopped\_at |
| :---- | :---- | :---- | :---- | :---- | :---- |

notification-log

| id | receiver-user-id | workbench-id | instance-id | limit-id(fk) | created\_at |
| :---- | :---- | :---- | :---- | :---- | :---- |

workbench

| id | workbench-name | description | created\_at |
| :---- | :---- | :---- | :---- |

ami 

| ami-id(pk) | base OS | regions | created\_at |
| :---- | :---- | :---- | :---- |

role-limits

| id | role-id | limit\_type\_id | limit\_value |
| :---- | :---- | :---- | :---- |

