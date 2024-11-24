CREATE DATABASE delivery

USE delivery

CREATE FUNCTION fn_pratoscomingredientes(@nomePrato VARCHAR(100))
RETURNS @resultado TABLE (
    id VARCHAR(9),
    tipo VARCHAR(20),
    nome VARCHAR(100),
    valorBase FLOAT,
    ingredientes VARCHAR(MAX)
)
AS
BEGIN
    DECLARE @id VARCHAR(9),
            @tipo VARCHAR(20),
            @nome VARCHAR(100),
            @valorBase FLOAT,
            @ingredientes VARCHAR(MAX)

    DECLARE prato_cursor CURSOR FOR
    SELECT p.id, p.tipo, p.nome, p.valorBase
    FROM prato p
    WHERE @nomePrato = '.' OR p.nome = @nomePrato

    OPEN prato_cursor
    FETCH NEXT FROM prato_cursor INTO @id, @tipo, @nome, @valorBase

    WHILE @@FETCH_STATUS = 0
    BEGIN
        SELECT @ingredientes = STRING_AGG(i.nome, ', ')
        FROM pratoIngrediente pi
        INNER JOIN ingrediente i ON pi.ingredienteNome = i.nome
        WHERE pi.pratoCodigo = @id

        INSERT INTO @resultado (id, tipo, nome, valorBase, ingredientes)
        VALUES (@id, @tipo, @nome, @valorBase, ISNULL(@ingredientes, ''))

        FETCH NEXT FROM prato_cursor INTO @id, @tipo, @nome, @valorBase
    END

    CLOSE prato_cursor
    DEALLOCATE prato_cursor

    RETURN
END

