do 
$$
declare
	curr_time_in_ms bigint;
	test varchar;
	doc_obj jsonb;
	random_name text;
	random_uuid text;
	auto_id text;
	number_of_docs int := <document number>;
	number_of_folder int := <folder number>;
	parent_id text := '<parent folder id>';
	sample_doc jsonb := '{
						  "p": {
							"cb": "<client-id>",
							"bti": "cmis:document",
							"lmb": "<client-id>",
							"oti": "cmis:document",
							"s:o": "<client-id>",
							"icmke": false,
							"s:pids": []
						  },
						  "ct": "0",
						  "acl": [
							{
							  "prmn": 30,
							  "prcpl": "{sap:builtin}everyone"
							}
						  ],
						  "quTy": [
							"cmis:document"
						  ],
						  "cvers": 1
						}';
	sample_folder jsonb := '{
							  "p": {
								"cb": "<client-id>",
								"bti": "cmis:folder",
								"lmb": "<client-id>",
								"oti": "cmis:folder",
								"s:o": "<client-id>",
								"s:pids": []
							  },
							  "ct": "0",
							  "acl": [
								{
								  "prmn": 30,
								  "prcpl": "{sap:builtin}everyone"
								}
							  ],
							  "quTy": [
								"cmis:folder"
							  ],
							  "cvers": 1
							}';
begin 
	for counter in 1..number_of_docs loop
		select round(extract(epoch from now()) * 1000)::bigint into curr_time_in_ms;
		select uuid_generate_v4()::text into random_uuid;
		SELECT md5(random()::text) into random_name;
		select substring(md5(random()::text), 1, 24) into auto_id;
		doc_obj = jsonb_set(
				jsonb_set(
					jsonb_set(
						jsonb_set(
							jsonb_set(
								sample_doc, 
								'{p,s:pids,0}', 
								to_jsonb(parent_id)
							), 
							'{p,n}',
							to_jsonb(random_name)
						),
						'{p,cd}',
						to_jsonb(curr_time_in_ms)
					),
					'{p,oi}',
					to_jsonb(random_uuid)
				),
				'{p,lmd}',
				to_jsonb(curr_time_in_ms)
			);
		insert into ecm_<docs table uuid>_docs(_id, doc) values (auto_id, doc_obj);
					
		raise notice 'name: %', doc_obj #>> '{p,n}';
	end loop;
	
	for counter in 1..number_of_folder loop
		select round(extract(epoch from now()) * 1000)::bigint into curr_time_in_ms;
		select uuid_generate_v4()::text into random_uuid;
		SELECT md5(random()::text) into random_name;
		select substring(md5(random()::text), 1, 24) into auto_id;
		doc_obj = jsonb_set(
				jsonb_set(
					jsonb_set(
						jsonb_set(
							jsonb_set(
								jsonb_set(
									sample_folder, 
									'{p,pi}', 
									to_jsonb(parent_id)
								), 
								'{p,s:pids,0}', 
								to_jsonb(parent_id)
							), 
							'{p,n}',
							to_jsonb(random_name)
						),
						'{p,cd}',
						to_jsonb(curr_time_in_ms)
					),
					'{p,oi}',
					to_jsonb(random_uuid)
				),
				'{p,lmd}',
				to_jsonb(curr_time_in_ms)
			);
		insert into ecm_<docs table uuid>_docs(_id, doc) values (auto_id, doc_obj);
		
		raise notice 'name: %', doc_obj #>> '{p,n}';
	end loop;
end; 
$$