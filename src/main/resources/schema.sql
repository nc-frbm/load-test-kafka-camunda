--liquibase formatted sql
--changeset validation:4.0.2.1

create sequence if not exists hibernate_sequence;

create table if not exists t_additional_document
(
    sid                 bigint not null,
    declaration_id      varchar(255),
    declaration_version numeric(19, 2),
    identifier          varchar(255),
    message_id          varchar(255),
    type                varchar(255),
    constraint t_additional_document_pkey
        primary key (sid)
);

create index if not exists idx_message_id
    on t_additional_document (message_id);

create index if not exists idx_declaration_id_declaration_version
    on t_additional_document (declaration_id, declaration_version);

create index if not exists idx_declaration_version
    on t_additional_document (declaration_version);

create table if not exists t_amendment
(
    id                  bigint not null,
    action_type         varchar(255),
    reason_type         varchar(255),
    created_by          varchar(255),
    declaration_id      varchar(255),
    declaration_version numeric(19, 2),
    effective_date      timestamp,
    original_value      varchar(255),
    pointer             varchar(255),
    reason_language     varchar(255),
    reason_text         varchar(255),
    sequence_number     numeric(19, 2),
    timestamp           timestamp,
    amend_value         varchar(255),
    constraint t_amendment_pkey
        primary key (id)
);

create index if not exists amendment_declaration_id_declaration_version_idx
    on t_amendment (declaration_id, declaration_version);

create table if not exists t_declarations
(
    message_id          varchar(255) not null,
    declaration_id      varchar(255),
    declaration         text,
    declaration_version numeric(19, 2),
    receive_date        timestamp,
    submitter_reference varchar(255),
    constraint t_declarations_pkey
        primary key (message_id)
);

create table if not exists t_document_presentation_data
(
    sid                                 bigint not null,
    additional_document_sequence_number numeric(19, 2),
    comment                             varchar(255),
    control_instruction_sequence_number numeric(19, 2),
    control_task_id                     varchar(255),
    created_date                        timestamp,
    declaration_id                      varchar(255),
    due_date                            timestamp,
    goods_item_line_number              numeric(19, 2),
    is_in_complete_preferential         boolean,
    receipt_date                        timestamp,
    request_date                        timestamp,
    constraint t_document_presentation_data_pkey
        primary key (sid)
);

create table if not exists t_previous_document
(
    sid                 bigint not null,
    declaration_id      varchar(255),
    declaration_version numeric(19, 2),
    id                  varchar(255),
    message_id          varchar(255),
    type                varchar(255),
    constraint t_previous_document_pkey
        primary key (sid)
);

create table if not exists t_processing_status
(
    sid                           bigint not null,
    acceptance_date               timestamp,
    additional_message_id         varchar(255),
    created_by                    varchar(255),
    created_date                  timestamp,
    declaration_id                varchar(255),
    declaration_version           varchar(255),
    header_id                     varchar(255),
    invalid_date                  timestamp,
    processing_status_reason_type varchar(255),
    processing_status_type        varchar(255),
    reason_info                   varchar(255),
    version                       numeric(19, 2),
    constraint t_processing_status_pkey
        primary key (sid)
);

create index if not exists processing_status_index
    on t_processing_status (declaration_id);

create table if not exists t_validation_results
(
    id                     bigserial not null,
    declaration_id         varchar(255),
    declaration_version    numeric(19, 2),
    information_list_json  text,
    is_rejecting           boolean,
    message_id             varchar(255),
    pointers_list_json     text,
    qualifier              varchar(255),
    validation_result_type varchar(255),
    violated_rule_json     text,
    constraint t_validation_results_pkey
        primary key (id)
);



create table if not exists t_validation_results
(
    id                     bigserial not null,
    declaration_id         varchar(255),
    declaration_version    numeric(19, 2),
    information_list_json  text,
    is_rejecting           boolean,
    message_id             varchar(255),
    pointers_list_json     text,
    qualifier              varchar(255),
    validation_result_type varchar(255),
    violated_rule_json     text,
    constraint t_validation_results_pkey
        primary key (id)
);

create table if not exists t_obligation_guarantee
(
    id                     		bigserial not null,
    grn         		   		varchar(255),
    obligation_guarantee_json   text,
    type             			varchar(255),
    isPrioritized     			boolean,
    isDummy              		boolean,
    isApplicableForThirdParties boolean,
    messageId     				varchar(255),
    constraint t_obligation_guarantee_pkey
        primary key (id)
)