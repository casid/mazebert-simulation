package com.mazebert.simulation.units.creeps.effects;


import com.mazebert.java8.Consumer;
import com.mazebert.simulation.Context;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationValidator;
import com.mazebert.simulation.listeners.OnBonusRoundFinishedListener;
import com.mazebert.simulation.listeners.OnBonusRoundSurvivedListener;
import com.mazebert.simulation.replay.StreamReplayReader;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public strictfp class TimeLordBalancingTester {
    private static final String[] games = {
            "00a077c5-ec59-4903-923d-ec42945f2161-26478.mbg",
            "02c6267e-cb9d-4589-b892-3e911baff46e-27189.mbg",
            "03e7235a-392a-48f6-81fa-d29830d16a42-33911.mbg",
            "048d57a4-c120-4a16-8156-95bbc031b20e-35305.mbg",
            "048d57a4-c120-4a16-8156-95bbc031b20e-35408.mbg",
            "054e7ae8-ae85-47dd-88c4-97b0de416971-35662.mbg",
            "06494d2c-10c6-451b-846f-34f7e706733a-35664.mbg",
            "088af5f0-615b-4855-9370-f2c0d0578d66-35570.mbg",
            "0a1c1d47-3976-49b6-bc1a-4e9ecc4abe1f-17113.mbg",
            "0a85b696-576e-40eb-8e77-180be1abb30e-17585.mbg",
            "0a9e4bdb-4ed4-4e91-9d9c-64376b6764c8-35226.mbg",
            "0ab6ed60-75eb-4254-bae2-1385969b7905-18423.mbg",
            "0c6e1f5c-3b97-4be6-842b-cb8ee6afc5d8-21759.mbg",
            "0cb6ebbc-6680-4ed3-a049-46dd47c7450d-27697.mbg",
            "0cfe1289-3075-4291-b13c-791dd8102c80-27109.mbg",
            "0ef2bcf7-2a6a-4424-b642-d5ee186da777-35498.mbg",
            "0f7f37ab-8289-4fb4-943c-03fb66889e77-27189.mbg",
            "150079b7-a70a-44bf-b1be-3719f127f4d0-35331.mbg",
            "1575db4a-1839-4180-846f-e11b5387aa84-23688.mbg",
            "172a3330-9dbc-479f-9c63-d57ac01bc6db-35331.mbg",
            "18868265-61a9-4a85-a518-d28b5b2451e9-35686.mbg",
            "1939d83d-361f-4102-aefe-689a081cb7a3-31037.mbg",
            "19c63e06-4f29-426f-b553-cfddb4594eca-35331.mbg",
            "1c10102a-00d2-4596-a184-8bd85109151e-35331.mbg",
            "1d21a5ec-3749-4988-ac23-7a94c6528f94-35624.mbg",
            "1d94f47e-94b0-46fc-90e4-ff9357ee238f-27392.mbg",
            "1e895463-0a19-4fa1-b741-411084afcc95-21950.mbg",
            "202d266e-7bfe-4d07-abaf-77a975589354-17113.mbg",
            "20dfd640-a79b-468a-af27-994381e9eacb-26478.mbg",
            "22a326fb-a877-46f2-824d-76df8ac5e94c-27189.mbg",
            "22e3e2a2-7f72-4b5e-ae4d-2fe556d02ccc-35541.mbg",
            "2328eda0-3d16-45ae-bec9-44621513b85a-35537.mbg",
            "236c0067-88b5-4c31-b1e8-c2cf75330926-27392.mbg",
            "236c0067-88b5-4c31-b1e8-c2cf75330926-33649.mbg",
            "28746aeb-777f-4402-94ad-afce17ec1d5c-35180.mbg",
            "288fa1bb-3448-42d7-9f97-42eb65246d64-18423.mbg",
            "28a97ce4-dbc6-4711-9865-5201511a797f-34877.mbg",
            "28a97ce4-dbc6-4711-9865-5201511a797f-35533.mbg",
            "29e44d15-0d50-4694-9ef8-7d3731009ea2-13732.mbg",
            "2a3fb3fe-5d45-444d-94b1-9d381946baca-13732.mbg",
            "2b30ee97-9fde-42af-8f87-72ea51779f2b-23688.mbg",
            "2b6315a7-7ccd-4c8c-afa7-6415919838b4-21759.mbg",
            "2bc3343d-f26a-40ce-8ff6-115126424c49-34853.mbg",
            "2bc3343d-f26a-40ce-8ff6-115126424c49-35507.mbg",
            "2c6a6610-2dac-4d13-9e57-8d7d5240a0f5-35799.mbg",
            "2f7214a8-d97c-44be-b1c8-6a13604e558a-35624.mbg",
            "301fd1b9-8de9-41a6-8ede-0faba926e838-33629.mbg",
            "30eb06b7-76a8-4ac3-9c69-2dbcff8d8723-35331.mbg",
            "32ba8750-1c85-4ca0-ace2-3a8aedb06fb0-35521.mbg",
            "3349aa23-8758-4096-ab06-fb2fb99e370d-17113.mbg",
            "38ef6ce6-de1b-417c-b3d7-59e7712084c6-28253.mbg",
            "38ffb8e1-9da0-41c0-b5aa-bfa2f1523db1-27189.mbg",
            "3a6cd04d-9b96-4cfb-9c73-3b97a770e542-5765.mbg",
            "3a6cd04d-9b96-4cfb-9c73-3b97a770e542-35453.mbg",
            "40d76a51-8e91-44fa-925f-51703dc88d94-35222.mbg",
            "412f380a-4f85-4a13-8270-818d92a02d8a-34853.mbg",
            "412f380a-4f85-4a13-8270-818d92a02d8a-35507.mbg",
            "41ce8e91-1b51-4a36-9d2b-0e93bc5c1de0-35662.mbg",
            "4250bf4b-fc9c-4805-a3d9-ba34bac408b0-21610.mbg",
            "43d7497e-3e60-4d8e-9fae-c56386fb2715-17113.mbg",
            "448910b3-31c2-4ed9-a9c3-c02222516407-24627.mbg",
            "45426c85-7a4c-401e-b84a-6ead1a82136c-31037.mbg",
            "4572c177-0182-4443-ab91-97171204f7f1-35478.mbg",
            "47967d9f-9dd5-4686-b8cf-7438ed2c8095-35570.mbg",
            "479853c5-fb59-4d3f-9def-96264f03b206-33649.mbg",
            "48138888-98ad-477d-bd67-45cff5c21cb9-15662.mbg",
            "4cca5ff5-0390-4941-8f96-a32d637060c8-35049.mbg",
            "4e102f28-9d43-4f55-bfd3-c04431cc3c75-35544.mbg",
            "4e7c9363-b9a9-4ff7-a1ea-5dc6dfc90226-21950.mbg",
            "4ff7062a-b439-4d7e-bc4a-007a7004a488-35757.mbg",
            "50c65033-b180-4143-9835-e0bf1502fa17-35275.mbg",
            "53721793-16d2-4aa7-b2da-a70594746d2a-31037.mbg",
            "54bc1bfd-e697-459e-b3d2-c83c69fdc2a9-35537.mbg",
            "5a829d99-f841-4f94-9b32-500f721a8afd-35541.mbg",
            "5a83dced-5b50-460c-9827-1d3858d76d99-23688.mbg",
            "600ec647-8ac7-4503-92f1-8673f9721918-27189.mbg",
            "61181005-ac10-41e0-bdfc-72ed146ec9e7-33911.mbg",
            "61590917-4e7c-4d2e-ae29-41e4fe7a04bc-35275.mbg",
            "6174508a-2ef6-49f7-99c6-fec86078ddb2-35662.mbg",
            "6210a7fa-70cc-4231-86ef-ca8a1e5b6d65-33236.mbg",
            "62ffc7f9-a81f-450d-a368-13c4dfe79044-27392.mbg",
            "62ffc7f9-a81f-450d-a368-13c4dfe79044-33649.mbg",
            "6354eb80-2885-47b8-bf61-7d89b456ce1f-35521.mbg",
            "65134089-3615-44b0-941c-bdc402b5f119-27392.mbg",
            "65b292da-817f-4b3a-be06-75a9af385d4c-33911.mbg",
            "6918a2bd-9875-4165-aa1a-b64c35c31ce2-21950.mbg",
            "699eeaa2-7a20-444d-aae3-6cc982da93a1-35498.mbg",
            "6a54d7d1-0661-4da1-883a-0db278109b87-35662.mbg",
            "6a815200-b42d-4ad6-b5da-93c08ac27a21-35662.mbg",
            "6ac72b7a-6843-4a1b-a0b5-45401ec80ab2-24627.mbg",
            "6d99e0db-cd8b-48ac-96ed-32e2a31eab83-35498.mbg",
            "6e5f40f2-6e25-4a31-bac9-abf0142fb756-35331.mbg",
            "6e5f40f2-6e25-4a31-bac9-abf0142fb756-35694.mbg",
            "6fadfad6-a3bb-48a0-bbaf-ddeef9df38a0-27419.mbg",
            "6ff39c64-63b2-4698-b147-cd1b4a220ab1-19686.mbg",
            "6ffc484f-9752-426e-a47c-c2e716f472a4-35331.mbg",
            "70b0bceb-cd80-4cc1-83c0-848a7abb21ff-17585.mbg",
            "715665ac-f8aa-4d0b-90bc-c909ccc5eba8-35305.mbg",
            "715665ac-f8aa-4d0b-90bc-c909ccc5eba8-35408.mbg",
            "71706236-8a75-401f-91f7-af88f4274e01-33875.mbg",
            "71706236-8a75-401f-91f7-af88f4274e01-35579.mbg",
            "71eb4dcf-69f2-406e-bf8d-0b79087bb6ac-34853.mbg",
            "71eb4dcf-69f2-406e-bf8d-0b79087bb6ac-35507.mbg",
            "7209ee41-0cfd-4e09-85b3-e13703d06544-35049.mbg",
            "724e2478-8f58-4d58-a54d-ccd2e9770e76-24627.mbg",
            "7903a86f-6fac-4e9c-a3d2-1d2dd901982d-13732.mbg",
            "79974791-a5e5-4b72-8f0d-c11f7757ac42-23688.mbg",
            "7a1d9d9c-5441-49f7-bfa8-5da06dc7609a-21759.mbg",
            "7c625bd4-a4fe-42ec-bdbf-f2edb46835a0-35624.mbg",
            "7c75fb80-e0a6-4a72-9e36-11ca9cf9fda1-33911.mbg",
            "7d225eb5-7084-4989-a0a3-ea0d2db9a402-32226.mbg",
            "7d35f36e-85a4-4a7f-a65a-7c8bf38e9ab8-33649.mbg",
            "7d54fd28-b5f7-405a-8832-4b47aa0ddf07-33649.mbg",
            "7e8a5e2e-4ddc-4a82-9f4f-323201e8e22b-35631.mbg",
            "7f753bc9-6cf7-4711-b90f-fc556c2da966-35507.mbg",
            "7f9db78d-d076-4584-a904-e5cb3a31fdb7-17585.mbg",
            "802caeca-0588-43be-be21-f8c1aae4d7e2-35570.mbg",
            "802f3241-73ee-4edf-a39d-b94c575bc790-35049.mbg",
            "80325d66-7beb-48cc-b4be-d699a6ab78ff-35331.mbg",
            "80325d66-7beb-48cc-b4be-d699a6ab78ff-35694.mbg",
            "8138f171-6626-4af8-8366-1f363fec561b-35624.mbg",
            "829cb6ef-95d1-4be1-b500-3359e907ea02-33875.mbg",
            "841e0bb1-a981-40dc-9afe-e739e8c6d5a2-33749.mbg",
            "8517831d-f4a9-48d5-8411-fe86bc0dbad4-13732.mbg",
            "865e8c54-fe54-4ace-9fab-93a89ae05a4f-26478.mbg",
            "865e8c54-fe54-4ace-9fab-93a89ae05a4f-35778.mbg",
            "86882466-8bcd-4c2b-8856-9332a0379b7b-31037.mbg",
            "87a4fbd8-5181-4de2-bc69-8b9c888680fe-35498.mbg",
            "88ec96bf-554b-4d0a-a67b-009a3d1f2231-33911.mbg",
            "895dfc92-216f-470d-a6c6-ae014578b855-15512.mbg",
            "8a47d99f-5102-4b77-8962-b7699923b80e-35570.mbg",
            "8a5aaece-324c-41e0-aef1-5dfb6673f4d7-35624.mbg",
            "8ba03678-89c2-4513-9ed9-0d1f0b9cf557-35180.mbg",
            "8bdd5da4-7d55-4816-a0cf-5ec97aad6004-35537.mbg",
            "8d9dd4d6-9db0-4eaa-a077-b6532927db20-24971.mbg",
            "8d9e9b21-ff1a-45fc-b811-00231527225f-35602.mbg",
            "8f7a116d-02fe-462e-ae4b-3dfdc0d1c6c6-33649.mbg",
            "8f83222d-f073-4dd9-b16f-dbb0c21d405a-31037.mbg",
            "911dfdb4-e98b-4091-9dbd-463b5abfc3c6-35662.mbg",
            "9309a1b9-9f7c-49f8-8934-eea2c416b1d4-35222.mbg",
            "947258be-d984-4904-9021-99b14ab14693-35305.mbg",
            "947258be-d984-4904-9021-99b14ab14693-35408.mbg",
            "955b9f86-01c0-4876-80fc-8926e6e264f3-35180.mbg",
            "95f16830-349d-4ded-aee0-e1c800163b68-22120.mbg",
            "973c26e0-5bc1-4536-9922-0deafeb2643d-33875.mbg",
            "973c26e0-5bc1-4536-9922-0deafeb2643d-35579.mbg",
            "989a677d-9cc0-4aa2-b8e7-2627d9c29c87-35428.mbg",
            "98b71b8a-e1a6-4293-ace2-35066b3b02b5-17113.mbg",
            "99234b69-c9aa-4b78-a4db-518c501de2c1-17113.mbg",
            "9a3a9a89-ec33-47a4-9928-d217301185c4-35275.mbg",
            "9be55b77-d6f6-4fbb-b359-26b4989c40cc-35624.mbg",
            "9c4dbacb-703d-4a02-81ea-188f01ba665b-27697.mbg",
            "a0894b7b-8577-4083-a11f-5faf73450b33-13732.mbg",
            "a1462687-6c1c-4502-8ebd-cd416eb7712a-35624.mbg",
            "a384c8a6-a092-4bee-933b-0026290a8fe7-33649.mbg",
            "a3ee574f-d06c-4d6a-a577-b42c10d6edef-35521.mbg",
            "a42354fa-1d1d-486d-bf7d-6311f997bf3e-35544.mbg",
            "a51babf0-4e39-42e2-921e-faa85b1bad2f-35331.mbg",
            "a62bfe04-c920-4404-8965-de7dc3e2c827-33875.mbg",
            "aa63425f-c55d-4700-aa0d-f4941a481ccb-21950.mbg",
            "ac29cf6e-ff00-47d2-a318-73833bf0a3da-118.mbg",
            "ac29cf6e-ff00-47d2-a318-73833bf0a3da-35704.mbg",
            "ac3f61f7-b7d6-4032-9208-743ee7f5aaeb-32226.mbg",
            "aeb33cae-c777-4af8-95f2-471d629654ef-35331.mbg",
            "aed2e27e-6f7b-4c96-8706-0efae9a02506-17113.mbg",
            "b13618de-55c2-4977-a5b5-af9378e7353c-35331.mbg",
            "b19ee046-c7f4-4ff7-b210-ebb5fa09e0eb-21950.mbg",
            "b2e69756-53b3-4280-b255-ec26129c515a-35537.mbg",
            "b53dd0dc-dfff-4dc3-86f1-92d71d13a934-35570.mbg",
            "b59dae88-bdba-4de4-a956-d9daecbda481-35331.mbg",
            "b81d3e3e-0026-4f4a-8b58-f4ca1742ce24-13732.mbg",
            "b96aae7c-cd4e-49ec-9aae-b62084e52294-35541.mbg",
            "ba13c80a-855e-42b7-839c-051c8a1697a1-14053.mbg",
            "bb2b9b37-a9f4-4d3c-a104-dd70bed13f6a-26478.mbg",
            "bb2b9b37-a9f4-4d3c-a104-dd70bed13f6a-30585.mbg",
            "bc27639a-4d6f-4f4a-8a7f-83bf67164b2d-35631.mbg",
            "bc37ede1-ff16-4904-8e5e-c8a4a2075385-35662.mbg",
            "bda08bf4-b62c-4a21-b93b-2c242dafcb02-33749.mbg",
            "bdbb18b2-5a69-4924-913b-2a0d7465948b-31037.mbg",
            "bfb22498-5394-492a-8335-31ec57d371c6-34316.mbg",
            "c1ff8ffa-7937-4f6b-92c0-b0957c42731e-27189.mbg",
            "c21d3c12-f413-4123-ba98-974a056ed45e-35275.mbg",
            "c3179946-17bf-4dd7-a471-65716e256707-33875.mbg",
            "c3179946-17bf-4dd7-a471-65716e256707-35689.mbg",
            "c4ee1350-57b2-4267-b6a8-d20aa036a352-33911.mbg",
            "c5fe20d8-8122-419b-8e9d-f326c5825d5f-35624.mbg",
            "c70e21d6-722e-41ee-9544-dac3ad13f0fc-35602.mbg",
            "c7538509-0459-4d5e-ada2-71cca13fbbc5-19686.mbg",
            "c7649884-48c0-4b5f-99f3-2ea4c7fa5e12-34530.mbg",
            "c7d622bd-a2a7-4e81-9a20-986eb8fd9fe8-27697.mbg",
            "ca09aa8d-2d43-4589-ad01-25fa61942906-23688.mbg",
            "cf5700ab-917f-42bc-924d-b66f46b43323-33911.mbg",
            "d091f430-cc41-4225-ab6e-eecd7e55f740-31037.mbg",
            "d11c7f13-4e8c-4200-993a-8e3e126e5bf3-35624.mbg",
            "d172795d-41c9-446a-80c7-f2d7f785d97c-35331.mbg",
            "d29aba01-1986-4114-954d-3cfa8336ae9f-35331.mbg",
            "d43d5271-6c69-4cec-8c42-003c1217531b-35624.mbg",
            "d7e9ef89-dc5b-4841-b9fa-628ad13bdc81-33649.mbg",
            "d90d4570-b0b0-411c-91c0-c7bba634e82e-33911.mbg",
            "d9e770c2-3d89-45e6-8f7f-0a9cc9b6a621-35544.mbg",
            "da2a3bb6-be62-46d7-b6d3-64275c3a364f-13732.mbg",
            "dab04f58-18c2-4104-8a3d-6fbc436c92d8-35624.mbg",
            "dc33ec9c-2ea5-45fc-98eb-7d75e3995944-27189.mbg",
            "dd106f31-35e3-412b-a68c-9de33d6576ab-35664.mbg",
            "dffc58f4-11c9-473c-977c-1cf21feb74d4-17113.mbg",
            "e1412e16-ae82-4bba-b61a-9abbd8f8fbdd-35226.mbg",
            "e19f1874-78af-42f8-8cfc-d7e32551d8c2-33749.mbg",
            "e1adbfed-36fb-45cb-9e58-5cba41ba0c15-33649.mbg",
            "e27acc35-74a7-4963-a718-2311a80dedcb-33649.mbg",
            "e4bddf94-d759-4e21-ba8e-dfa4b669cd4d-35544.mbg",
            "e5b3a913-e413-4d39-bb69-34e3988a3633-35226.mbg",
            "e757ec08-e9a3-41ba-ac2b-0060afa98a55-34877.mbg",
            "e757ec08-e9a3-41ba-ac2b-0060afa98a55-35533.mbg",
            "e771aa0b-c6c6-4d31-bc36-22a811b9f832-35498.mbg",
            "e7f5b648-8cd6-43b9-8462-a6dd3cd03de5-26478.mbg",
            "e8d47d33-2d80-4a22-be9e-48b1aea67aed-35570.mbg",
            "e912a1df-b0bf-4b27-b828-3035a725668d-29772.mbg",
            "e933617e-ff46-413d-bb8d-99f08d97e282-27189.mbg",
            "e9adaf0d-d7eb-473a-92f4-12fb511266f2-33649.mbg",
            "eaa78d02-ad00-43f4-94ac-c9c068b8470e-22351.mbg",
            "eb31a1bd-481d-447e-898a-2ade34698b60-26478.mbg",
            "ec73e5e8-5aa1-44e1-889a-fbe4bbb65e97-13732.mbg",
            "ed6a23ba-47bf-44c6-bd36-1d57aa2f8531-17585.mbg",
            "ef1e0c75-5a1b-4a1c-aced-682533b5d47c-27392.mbg",
            "f031e7e8-eab9-48e1-b9fc-fee1f0e31b60-33717.mbg",
            "f0c4d56f-f187-46a3-bc4d-4616ee95993e-35624.mbg",
            "f283f5d6-d8bf-42c8-9111-5d2fb2ed995d-35570.mbg",
            "f28c15c5-504c-49e3-9864-9390ffde2e25-35226.mbg",
            "f2c255c2-236f-41b9-9723-8a7ad78fe8f7-14969.mbg",
            "f30f60b4-ec67-415f-9a9f-cbea3452661c-35478.mbg",
            "f5440d0b-8c70-459d-b9ab-0b5bc59af8eb-18423.mbg",
            "f5b405fd-69f7-4291-9c06-1773b49e9b06-34853.mbg",
            "f7f55517-1746-4aea-b87d-9d370eb2c4c2-35570.mbg",
            "f9d71a26-6584-4d57-a170-e4a87735b20f-35537.mbg",
            "fa08a42b-7769-4e88-a519-bb851340a52d-35662.mbg",
            "faf313fb-0ea8-4715-b6ec-a207cdefa505-35570.mbg",
            "fb207d5f-c758-41e9-ac80-7f94a7366278-17585.mbg",
            "fda1c0d3-3e43-4b47-b08e-bf0b47b7c815-22351.mbg",
            "fe222cf1-f297-444d-b164-11c033b7fc15-35602.mbg",
            "ff1fd0f4-ee39-479d-a797-df930006dc47-24627.mbg"
    };

    private static final int[] secondsSurvived = {
            17596,
            49429,
            5770,
            322340,
            322340,
            12404,
            9756,
            6939,
            413907,
            14250,
            5144,
            141130,
            12647,
            19538,
            13276,
            8974,
            241440,
            7808,
            16684,
            5909,
            5006,
            5920,
            5484,
            7322,
            15175,
            6242,
            123472,
            364788,
            1010790,
            14873,
            300814,
            28234,
            11864,
            11864,
            8304,
            31246,
            7838,
            7838,
            17815,
            6420,
            11652,
            12353,
            18387,
            18387,
            6790,
            31424,
            17377,
            7681,
            17755,
            87642,
            5829,
            110266,
            53932,
            53932,
            6107,
            27814,
            27814,
            7775,
            5842,
            6928,
            272587,
            7784,
            10076,
            6301,
            10080,
            92572,
            35146,
            28069,
            11366,
            6514,
            10838,
            15172,
            21540,
            18099,
            33495,
            33325,
            6106,
            8420,
            17408,
            60590,
            19093,
            19093,
            51252,
            12717,
            9765,
            52887,
            25316,
            6931,
            5099,
            161162,
            6661,
            15423,
            14468,
            14468,
            14486,
            13467,
            8963,
            8828,
            106591,
            106591,
            8519,
            8519,
            271957,
            271957,
            26045,
            197748,
            13222,
            6069,
            18966,
            13319,
            13116,
            5935,
            80065,
            12868,
            7038,
            5676,
            8305,
            5048,
            14268,
            6332,
            6332,
            8653,
            9943,
            5207,
            7333,
            24639,
            24639,
            15712,
            8425,
            10022,
            6248,
            5708,
            6566,
            11049,
            63310,
            10354,
            5782,
            18956,
            10290,
            6012,
            167238,
            7412,
            7412,
            7182,
            6928,
            5388,
            5388,
            10675,
            8761,
            8712,
            8274,
            23529,
            270672,
            9662,
            13952,
            6373,
            6300,
            266692,
            5768,
            7833,
            902434,
            7328,
            7328,
            7005,
            13078,
            15120,
            8955,
            303330,
            21113,
            6393,
            11943,
            11061,
            52835,
            6493,
            6392,
            6392,
            6894,
            7121,
            5491,
            6834,
            14275,
            238594,
            9719,
            53476,
            53476,
            18844,
            10496,
            13388,
            887793,
            11917,
            28005,
            9576,
            10406,
            5116,
            17945,
            9090,
            10581,
            282034,
            96671,
            6930,
            34404,
            6840,
            28692,
            349474,
            7633,
            72921,
            6895,
            5276,
            18476,
            17853,
            51332,
            6084,
            6481,
            6481,
            8205,
            39013,
            7112,
            12850,
            13224,
            189935,
            23966,
            541302,
            18551,
            12524,
            13795,
            5084,
            125080,
            6354,
            6408,
            6509,
            9810,
            502969,
            6602,
            8971,
            18763,
            15003,
            10972,
            9212,
            10275,
            13693,
            233497
    };

    private static final Path gamesDirectory = Paths.get("games");

    @Test
    void simulateGames() {
        System.out.println("game, old seconds survived, new seconds survived");

        Arrays.stream(games).parallel().forEach(game -> checkGame(game, Sim.v17));
    }

    @SuppressWarnings({"SameParameterValue", "Convert2Lambda"})
    private void checkGame(String game, int version) {
        Path file = gamesDirectory.resolve("" + version).resolve(game);

        try (StreamReplayReader replayReader = new StreamReplayReader(new BufferedInputStream(Files.newInputStream(file, StandardOpenOption.READ)), version)) {
            new SimulationValidator().validate(version, replayReader, new Consumer<Context>() {
                @Override
                public void accept(Context context) {
                    context.simulationListeners.onBonusRoundSurvived.add(new OnBonusRoundSurvivedListener() {
                        @Override
                        public void onBonusRoundSurvived(int seconds) {
                            if (seconds >= 5000 && !context.gameGateway.getGame().timeLord) {
                                int timeLordStartTurnNumber = context.turnGateway.getCurrentTurnNumber();

                                context.newBalancing = true;

                                AtomicBoolean bonusRoundFinished = new AtomicBoolean();

                                context.simulationListeners.onBonusRoundFinished.add(new OnBonusRoundFinishedListener() {
                                    @Override
                                    public void onBonusRoundFinished() {
                                        bonusRoundFinished.set(true);
                                    }
                                });

                                context.simulation.setPause(1, false);
                                context.waveSpawner.startTimeLordCountDown();

                                while (!bonusRoundFinished.get()) {
                                    context.simulation.simulateTurn(Collections.emptyList(), false);
                                }

                                int gameIndex = getGameIndex(game);
                                synchronized (TimeLordBalancingTester.this) {
                                    System.out.println(games[gameIndex] + ", " + secondsSurvived[gameIndex] + ", " + context.gameGateway.getGame().bonusRoundSeconds + " (turn: " + timeLordStartTurnNumber + ")");
                                }

                                throw new RuntimeException("done");
                            }
                        }
                    });
                }
            }, null);
        } catch (RuntimeException e) {
            // Yes, we are done
        } catch (NoSuchFileException e) {
            //System.out.println("File not found " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getGameIndex(String game) {
        for (int i = 0; i < games.length; i++) {
            if (games[i].equals(game)) {
                return i;
            }
        }
        return -1;
    }
}